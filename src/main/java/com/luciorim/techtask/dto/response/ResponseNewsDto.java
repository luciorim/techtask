package com.luciorim.techtask.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseNewsDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("title_image")
    private String titleImageUrl;
}
