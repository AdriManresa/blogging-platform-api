package com.manresa.blogging_platform_api.dto.comment;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private String authorUsername; // Solo mandamos el nombre, no toda la entidad User
    private LocalDateTime createdAt;
}