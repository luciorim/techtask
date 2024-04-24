package com.luciorim.techtask.service.impl;

import com.luciorim.techtask.dto.request.RequestCreateOrderDto;
import com.luciorim.techtask.dto.response.ResponseOrderDto;
import com.luciorim.techtask.exceptions.DbObjectNotFoundException;
import com.luciorim.techtask.mapper.OrderMapper;
import com.luciorim.techtask.model.Order;
import com.luciorim.techtask.model.Product;
import com.luciorim.techtask.repository.OrderRepository;
import com.luciorim.techtask.repository.ProductRepository;
import com.luciorim.techtask.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.luciorim.techtask.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public void createOrder(RequestCreateOrderDto requestCreateOrderDto) {
        List<Long> productsId = requestCreateOrderDto.getProducts();
        if(productsId == null || productsId.isEmpty()) {
            throw new IllegalArgumentException("Product list cannot be empty");
        }

        Double totalPrice = 0.0;

        Map<Product, Integer> productsWithQuantity = new HashMap<>();
        for (Long productId : productsId) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Product with id " + productId + " not found"));
            productsWithQuantity.put(product, productsWithQuantity.getOrDefault(product, 0) + 1);
            totalPrice += product.getPrice();
        }

        for (Map.Entry<Product, Integer> entry : productsWithQuantity.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            if (product.getEnabledQuantity() < quantity || !product.getIsEnabled()) {
                throw new IllegalArgumentException("Product with id " + product.getId() + " hasn't enough quantity");
            }

            product.setEnabledQuantity(product.getEnabledQuantity() - quantity);
            if (product.getEnabledQuantity() == 0) {
                product.setIsEnabled(false);
            }
            productRepository.save(product);
        }

        Order order = new Order();
        order.setPhoneNumber(requestCreateOrderDto.getPhoneNumber());
        order.setCreatedAt(LocalDateTime.now(ZONE_ID));
        order.setIsPaid(false);
        order.setIsFinished(false);
        order.setIsDelivered(false);
        order.setDeliveryAddress(requestCreateOrderDto.getDeliveryAddress());
        order.setProductsId(productsId);
        order.setTotalPrice(totalPrice);
        order.setUserEmail(requestCreateOrderDto.getUserEmail());
        ;
        orderRepository.save(order);
    }

    @Override
    public List<ResponseOrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDto(orders);
    }

    @Override
    public ResponseOrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Order with id " + orderId + " not found"));

        return orderMapper.toDto(order);
    }

    @Override
    public void finishOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Order with id " + orderId + " not found"));

        if(!order.getIsDelivered() || !order.getIsPaid()) {
            throw new IllegalArgumentException("Order with id " + orderId + " is not delivered or paid!");
        }

        order.setIsFinished(true);
        orderRepository.save(order);

    }

    @Override
    public void orderPaidByID(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Order with id " + orderId + " not found"));

        order.setIsPaid(true);
        orderRepository.save(order);
    }

    @Override
    public void confirmDeliveryByID(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Order with id " + orderId + " not found"));

        order.setIsDelivered(true);
        orderRepository.save(order);
    }


}
