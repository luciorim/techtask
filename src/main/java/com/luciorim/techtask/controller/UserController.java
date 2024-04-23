package com.luciorim.techtask.controller;

import com.luciorim.techtask.dto.response.ResponseUserDto;
import com.luciorim.techtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getUser(@PathVariable Long id) {
        ResponseUserDto responseUserDto = userService.getUser(id);
        return ResponseEntity.ok(responseUserDto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

}
