package com.manresa.blogging_platform_api.service;

import com.manresa.blogging_platform_api.dto.post.PostRequest;
import com.manresa.blogging_platform_api.dto.post.PostResponse;
import com.manresa.blogging_platform_api.entity.Category;
import com.manresa.blogging_platform_api.entity.Post;
import com.manresa.blogging_platform_api.entity.User;
import com.manresa.blogging_platform_api.repository.CategoryRepository;
import com.manresa.blogging_platform_api.repository.PostRepository;
import com.manresa.blogging_platform_api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public PostResponse createPost(PostRequest request) {

        // A. Preparamos la caja vacía
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setTags(request.getTags());

        // B. Buscamos la Categoría usando el ID del Request y la asignamos
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("La categoría no existe con ID: " + request.getCategoryId()));
        post.setCategory(category);

        // C. Buscamos al Autor leyendo el token de seguridad y lo asignamos
        String usernameLogueado = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByUsername(usernameLogueado)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username: " + usernameLogueado));
        post.setAuthor(author);

        // D. Guardamos la publicación terminada en la base de datos
        Post savedPost = postRepository.save(post);

        // E. Armamos el paquete de respuesta usando el @Builder que creaste
        return PostResponse.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .categoryName(savedPost.getCategory().getName())
                .authorUsername(savedPost.getAuthor().getUsername())
                .tags(savedPost.getTags())
                .createdAt(savedPost.getCreatedAt())
                .updatedAt(savedPost.getUpdatedAt())
                .build();
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .categoryName(post.getCategory().getName())
                        .authorUsername(post.getAuthor().getUsername())
                        .tags(post.getTags())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .toList();
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado "));
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .categoryName(post.getCategory().getName())
                .authorUsername(post.getAuthor().getUsername())
                .tags(post.getTags())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
    public PostResponse updatePost(PostRequest request, long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado "));
        //modificamos los datos
        post.setTitle(request.getTitle());
       post.setContent(request.getContent());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("La categoría no existe con ID: " + request.getCategoryId()));
        post.setCategory(category);
        // Guardamos la publicación editada en la base de datos
        Post savedPost = postRepository.save(post);
        // E. Armamos el paquete de respuesta usando el @Builder que creaste
        return PostResponse.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .categoryName(category.getName())
                .tags(savedPost.getTags())
                .build();
    }

}
