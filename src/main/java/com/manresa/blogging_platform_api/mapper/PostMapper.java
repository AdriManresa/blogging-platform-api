package com.manresa.blogging_platform_api.mapper;

import com.manresa.blogging_platform_api.dto.post.PostResponse;
import com.manresa.blogging_platform_api.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponse toResponse(Post post) {
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
}