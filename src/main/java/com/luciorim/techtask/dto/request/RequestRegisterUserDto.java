package com.luciorim.techtask.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class RequestRegisterUserDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Size(min = 5, message = "Password should have minimum 5 characters")
    @NotNull
    private String password;

    @Size(min = 5, message = "Password should have minimum 5 characters")
    @NotNull
    private String confirmPassword;

    @NotNull
    private String email;

    private MultipartFile profilePicture;
}
