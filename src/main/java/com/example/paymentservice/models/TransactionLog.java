package com.example.paymentservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TransactionLog extends BaseModel {
    @Column(nullable = false)
    private String gatewayTransactionId;

    @Column(nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private TransactionEventType eventType; // Enum: PAYMENT_INITIATED, PAYMENT_COMPLETED, PAYMENT_FAILED, REFUND_INITIATED

    @Column(nullable = false)
    private PaymentStatus status;          // Status at the time of this event (PENDING, COMPLETED, FAILED)

    @Column(nullable = false)
    private Gateways paymentGateway; // Gateway used (STRIPE, RAZORPAY, etc.)

    @Column(nullable = false)
    private String details;                // Additional metadata (e.g., gateway responses, JSON)

    @Column(nullable = false)
    private Double amount;                 // Transaction amount (can vary for partial refunds, retries, etc.)

    @Column(nullable = false)
    private Currency currency;             // Currency of the transaction
}
