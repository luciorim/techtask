package com.luciorim.techtask.service;

import com.luciorim.techtask.dto.request.RequestAuthDto;
import com.luciorim.techtask.dto.request.RequestRegisterUserDto;
import com.luciorim.techtask.dto.response.ResponseAuthDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    void registerUser(RequestRegisterUserDto user);

    ResponseAuthDto authenticateUser(RequestAuthDto auth);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


}
