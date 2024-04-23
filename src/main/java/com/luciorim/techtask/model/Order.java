package com.luciorim.techtask.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_order")
public class Order extends BaseEntity {

    private Double totalPrice;

    private String phoneNumber;

    private Boolean isDelivered;

    private Boolean isPaid;

    private Boolean isFinished;

    private String deliveryAddress;

    private String userEmail;

    private List<Long> productsId;

}
