package com.example.paymentservice.dtos;

import lombok.Data;

@Data
public class UpdateOrderDto {
    private long userId;
    private String orderId;
    private String paymentStatus;
}
