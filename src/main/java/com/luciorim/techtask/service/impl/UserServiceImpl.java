package com.luciorim.techtask.service.impl;

import com.luciorim.techtask.constants.Role;
import com.luciorim.techtask.dto.request.RequestRegisterUserDto;
import com.luciorim.techtask.dto.response.ResponseUserDto;
import com.luciorim.techtask.exceptions.DbObjectNotFoundException;
import com.luciorim.techtask.mapper.UserMapper;
import com.luciorim.techtask.model.User;
import com.luciorim.techtask.repository.UserRepository;
import com.luciorim.techtask.service.ImageService;
import com.luciorim.techtask.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public ResponseUserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(DbObjectNotFoundException::new);
        return userMapper.toDto(user);
    }

    @Override
    public List<ResponseUserDto> getUsers() {
        List<User> users = userRepository.findAll();

        return userMapper.toDto(users);
    }
}
