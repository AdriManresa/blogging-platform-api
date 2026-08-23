package com.manresa.blogging_platform_api.dto.post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String categoryName;
    private String authorUsername;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}