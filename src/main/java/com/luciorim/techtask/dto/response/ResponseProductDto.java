package com.luciorim.techtask.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseProductDto {
    private Long id;
    private String name;
    private Double price;
    private Integer enabledQuantity;
    private Boolean isEnabled;

}
