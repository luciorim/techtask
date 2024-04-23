package com.luciorim.techtask.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestAuthDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
