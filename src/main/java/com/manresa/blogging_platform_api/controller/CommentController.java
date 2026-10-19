package com.manresa.blogging_platform_api.controller;

import com.manresa.blogging_platform_api.dto.comment.CommentRequest;
import com.manresa.blogging_platform_api.dto.comment.CommentResponse;
import com.manresa.blogging_platform_api.entity.Comment;
import com.manresa.blogging_platform_api.repository.CommentRepository;
import com.manresa.blogging_platform_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request,
            Principal principal
    ) {
        // Extraemos el username del token usando principal.getName()
        CommentResponse response = commentService.createComment(postId, request, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            Principal principal
    ) {
        // Llamamos al servicio pasando los 3 datos
        commentService.deleteComment(postId, commentId, principal.getName());

        // Devolvemos 204 No Content si se borró con éxito
        return ResponseEntity.noContent().build();
    }
}