package com.luciorim.techtask.mapper;

import com.luciorim.techtask.dto.response.ResponseOrderDto;
import com.luciorim.techtask.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, ResponseOrderDto> {
}
