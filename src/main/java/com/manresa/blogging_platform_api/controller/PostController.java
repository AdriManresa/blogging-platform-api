package com.manresa.blogging_platform_api.controller;

import com.manresa.blogging_platform_api.dto.post.PostRequest;
import com.manresa.blogging_platform_api.dto.post.PostResponse;
import com.manresa.blogging_platform_api.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request){
        //Le pasamos el paquete al técnico (el Service) para que lo procese
        PostResponse response = postService.createPost(request);
        //Devolvemos la respuesta al cliente con un código 201 (Creado)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        // Le pasamos al servicio los números que atrapamos de la URL (o los de defecto)
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById( @PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PutMapping ("/{id}")
    public ResponseEntity<PostResponse> updatePost(@RequestBody PostRequest request,@PathVariable Long id  ){
        return ResponseEntity.ok(postService.updatePost(request,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
