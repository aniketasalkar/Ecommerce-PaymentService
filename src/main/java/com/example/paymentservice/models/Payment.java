package com.example.paymentservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Payment extends BaseModel {

//    @Column(nullable = false)
    private String gatewayTransactionId;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private Gateways preferredPaymentGateway;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @Column(nullable = false)
    private long expiry;

    @Column(nullable = false)
    private String paymentLink;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long orderNumber;
}
