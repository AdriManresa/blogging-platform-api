package com.manresa.blogging_platform_api.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PostRequest {

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long categoryId;

    private List<String> tags;
}