package com.luciorim.techtask.service;

import com.luciorim.techtask.dto.request.RequestCreateProductDto;
import com.luciorim.techtask.dto.response.ResponseProductDto;

import java.util.List;

public interface ProductService {
    void createProduct(RequestCreateProductDto requestCreateProductDto);

    List<ResponseProductDto> getAllProducts();

    ResponseProductDto getProductById(Long id);

    void disableProductById(Long id);

}
