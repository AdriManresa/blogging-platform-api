package com.manresa.blogging_platform_api.service;

import com.manresa.blogging_platform_api.dto.category.CategoryRequest;
import com.manresa.blogging_platform_api.dto.category.CategoryResponse;
import com.manresa.blogging_platform_api.entity.Category;
import com.manresa.blogging_platform_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());

        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponse(savedCategory.getId(), savedCategory.getName());
    }

    public List<CategoryResponse> getAllCategories() {
        // 1. Traemos todas las entidades de la base de datos
        List<Category> categories = categoryRepository.findAll();

        // 2. Creamos una lista vacía para ir guardando nuestras respuestas DTO
        List<CategoryResponse> responses = new ArrayList<>();

        // 3. Recorremos cada entidad "category" que vino de la BD
        for (Category category : categories) {
            CategoryResponse categoryResponse = new CategoryResponse(category.getId(), category.getName());
            responses.add(categoryResponse);
        }
        // 4. Devolvemos la lista final ya transformada
        return responses;
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));

        // Devolvemos el DTO con los datos encontrados
        return new CategoryResponse(category.getId(), category.getName());
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        // 1. Buscamos la categoría existente. Si no está, explota controladamente.
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));

        // 2. Le cambiamos el nombre viejo por el nombre nuevo que nos mandaron
        category.setName(request.getName());

        // 3. Le pasamos el objeto modificado al repositorio para que lo guarde en PostgreSQL
        Category updatedCategory = categoryRepository.save(category);

        // 4. Devolvemos el DTO con los datos fresquitos
        return new CategoryResponse(updatedCategory.getId(), updatedCategory.getName());
    }
    public void deleteCategory(Long id) {
        // 1. Buscamos la categoría. Si no existe, lanzamos el error controlado.
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));

        // 2. Le ordenamos a la base de datos que la elimine
        categoryRepository.delete(category);
    }
}