package com.example.paymentservice.dtos;

import com.example.paymentservice.models.Currency;
import com.example.paymentservice.models.PaymentGateway;
import com.example.paymentservice.models.PaymentMode;
import com.example.paymentservice.models.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class InitiatePaymentResponseDto {
    private String paymentLink;

//    private String orderId;

//    private Date expiry;
}
