package com.luciorim.techtask.controller;

import com.luciorim.techtask.dto.request.RequestAuthDto;
import com.luciorim.techtask.dto.request.RequestRegisterUserDto;
import com.luciorim.techtask.dto.response.ResponseAuthDto;
import com.luciorim.techtask.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> registerUser(@Valid @ModelAttribute RequestRegisterUserDto requestRegisterUserDto){
        authService.registerUser(requestRegisterUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseAuthDto> loginUser(@Valid @RequestBody RequestAuthDto requestAuthDto){
        ResponseAuthDto responseAuthDto = authService.authenticateUser(requestAuthDto);
        return ResponseEntity.ok(responseAuthDto);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }
}
