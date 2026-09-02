package com.manresa.blogging_platform_api.mapper;

import com.manresa.blogging_platform_api.dto.category.CategoryResponse;
import com.manresa.blogging_platform_api.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}