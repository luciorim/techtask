package com.luciorim.techtask.controller;

import com.luciorim.techtask.dto.request.RequestCreateOrderDto;
import com.luciorim.techtask.dto.response.ResponseOrderDto;
import com.luciorim.techtask.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid @RequestBody RequestCreateOrderDto requestCreateOrderDto){
        var token = SecurityContextHolder.getContext().getAuthentication();
        requestCreateOrderDto.setUserEmail(token.getName());

        orderService.createOrder(requestCreateOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseOrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrderDto> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/delivered/{id}")
    public ResponseEntity<Void> deliveryOrder(@PathVariable Long id){
        orderService.confirmDeliveryByID(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/paid/{id}")
    public ResponseEntity<Void> confirmPay(@PathVariable Long id){
        orderService.orderPaidByID(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/finished/{id}")
    public ResponseEntity<Void> finishOrder(@PathVariable Long id){
        orderService.finishOrderById(id);
        return ResponseEntity.ok().build();
    }
}
