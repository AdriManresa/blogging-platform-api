package com.manresa.blogging_platform_api.service;

import com.manresa.blogging_platform_api.dto.comment.CommentRequest;
import com.manresa.blogging_platform_api.dto.comment.CommentResponse;
import com.manresa.blogging_platform_api.entity.Comment;
import com.manresa.blogging_platform_api.entity.Post;
import com.manresa.blogging_platform_api.entity.User;
import com.manresa.blogging_platform_api.repository.CommentRepository;
import com.manresa.blogging_platform_api.repository.PostRepository;
import com.manresa.blogging_platform_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResponse createComment(Long postId, CommentRequest request, String username) {
        // 1. Validar que el usuario exista
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Validar que el post exista
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        // 3. Armar la entidad a partir de los datos validados
        Comment comment = Comment.builder()
                .content(request.getContent())
                .author(author)
                .post(post)
                .build();

        // 4. Guardar en la base de datos
        Comment savedComment = commentRepository.save(comment);

        // 5. Empaquetar la respuesta
        return CommentResponse.builder()
                .id(savedComment.getId())
                .content(savedComment.getContent())
                .authorUsername(savedComment.getAuthor().getUsername())
                .createdAt(savedComment.getCreatedAt())
                .build();
    }
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .authorUsername(comment.getAuthor().getUsername())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .toList();
    }
    public void deleteComment(Long postId, Long commentId, String username) {
        // 1. Buscar el comentario en la base de datos
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        // 2. Validar que el comentario realmente pertenezca al post de la URL
        if (!comment.getPost().getId().equals(postId)) {
            throw new RuntimeException("El comentario no pertenece a esta publicación");
        }

        // 3. ¡Seguridad! Validar que el usuario del token sea el dueño del comentario
        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("No tenés permiso para borrar este comentario");
        }

        // 4. Si pasó todas las barreras, lo borramos
        commentRepository.delete(comment);
    }
}