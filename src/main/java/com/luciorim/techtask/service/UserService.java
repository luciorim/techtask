package com.luciorim.techtask.service;

import com.luciorim.techtask.dto.response.ResponseUserDto;

import java.util.List;

public interface UserService {

    ResponseUserDto getUser(Long id);

    List<ResponseUserDto> getUsers();

}
