package com.luciorim.techtask.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseOrderDto {
    private Long id;
    private String userEmail;
    private String deliveryAddress;
    private Boolean isPaid;
    private Boolean isDelivered;
    private Boolean isFinished;
    private Double totalPrice;
}
