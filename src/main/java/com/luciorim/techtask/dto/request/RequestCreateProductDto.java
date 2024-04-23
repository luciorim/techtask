package com.luciorim.techtask.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCreateProductDto {

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("price")
    @Positive(message = "Price should be positive")
    @NotNull
    private Double price;

    @JsonProperty("enabled_quantity")
    @Positive(message = "Quantity should be positive")
    private Integer enabledQuantity;

}
