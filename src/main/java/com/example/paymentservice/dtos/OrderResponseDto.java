package com.example.paymentservice.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long userId;

//    private List<OrderItemDto> orderItems;

    private double totalAmount;

    private String orderStatus;

    private String paymentStatus;

    private String paymentMethod;

    private Date orderDate;

    private Date expectedDeliveryDate;

//    private DeliverySnapshot deliverySnapshot;

    private String orderId;
}
