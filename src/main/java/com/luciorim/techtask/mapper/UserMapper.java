package com.luciorim.techtask.mapper;

import com.luciorim.techtask.dto.response.ResponseUserDto;
import com.luciorim.techtask.model.User;
import org.mapstruct.Mapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, ResponseUserDto> {

    @Override
    default ResponseUserDto toDto(User user) {
        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setId(user.getId());
        responseUserDto.setFirstName(user.getFirstName());
        responseUserDto.setLastName(user.getLastName());
        responseUserDto.setEmail(user.getEmail());
        if(user.getImageUrl() != null){
            responseUserDto.setProfileImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/api/images/" + user.getImageUrl());
        }
        return responseUserDto;
    }

}
