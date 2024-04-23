package com.luciorim.techtask.mapper;

import com.luciorim.techtask.dto.response.ResponseProductDto;
import com.luciorim.techtask.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ResponseProductDto>{

}
