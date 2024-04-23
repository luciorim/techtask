package com.luciorim.techtask.service;

import com.luciorim.techtask.dto.request.RequestCreateOrderDto;
import com.luciorim.techtask.dto.response.ResponseOrderDto;

import java.util.List;

public interface OrderService {
    void createOrder(RequestCreateOrderDto requestCreateOrderDto);

    List<ResponseOrderDto> getAllOrders();

    ResponseOrderDto getOrderById(Long orderId);

    void finishOrderById(Long orderId);

    void orderPaidByID(Long orderId);

    void confirmDeliveryByID(Long orderId);
}
