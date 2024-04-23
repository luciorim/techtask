package com.luciorim.techtask.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class RequestCreateNewsDto {
    @NotNull
    private String title;
    @NotNull
    private String content;
    private MultipartFile newsPicture;
}
