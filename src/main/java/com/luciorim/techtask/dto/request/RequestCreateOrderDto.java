package com.luciorim.techtask.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestCreateOrderDto {

    @JsonProperty("phone_number")
    @NotNull
    private String phoneNumber;

    @JsonProperty("delivery_address")
    @NotNull
    private String deliveryAddress;

    @JsonProperty("products")
    @NotNull
    private List<Long> products;

    @JsonProperty("user_email")
    private String userEmail;

}
