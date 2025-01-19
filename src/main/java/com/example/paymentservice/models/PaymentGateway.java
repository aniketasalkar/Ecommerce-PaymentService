package com.example.paymentservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PaymentGateway extends BaseModel {
//    @Column(nullable = false)
//    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Gateways paymentGateway;

    @Column(nullable = false)
    private GatewayStatus status;

    @Column(nullable = false)
    private float transactionFee;
}
