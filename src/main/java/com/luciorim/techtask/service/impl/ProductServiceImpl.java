package com.luciorim.techtask.service.impl;

import com.luciorim.techtask.dto.request.RequestCreateProductDto;
import com.luciorim.techtask.dto.response.ResponseProductDto;
import com.luciorim.techtask.exceptions.DbObjectNotFoundException;
import com.luciorim.techtask.mapper.ProductMapper;
import com.luciorim.techtask.mapper.ProductMapperImpl;
import com.luciorim.techtask.model.Product;
import com.luciorim.techtask.repository.ProductRepository;
import com.luciorim.techtask.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.luciorim.techtask.constants.ValueConstants.ZONE_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductMapperImpl productMapperImpl;

    @Override
    public void createProduct(RequestCreateProductDto requestCreateProductDto) {
        Product product = new Product();
        product.setName(requestCreateProductDto.getName());
        product.setPrice(requestCreateProductDto.getPrice());
        product.setCreatedAt(LocalDateTime.now(ZONE_ID));
        product.setEnabledQuantity(requestCreateProductDto.getEnabledQuantity());
        product.setIsEnabled(true);

        productRepository.save(product);
    }

    @Override
    public List<ResponseProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDto(products);
    }

    @Override
    public ResponseProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Product with id " + id + " not found"));

        return productMapper.toDto(product);
    }

    @Override
    public void disableProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Product with id " + id + " not found"));

        product.setIsEnabled(false);
    }
}
