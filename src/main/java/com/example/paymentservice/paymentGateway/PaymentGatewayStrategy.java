package com.example.paymentservice.paymentGateway;

import com.example.paymentservice.dtos.UserDto;
import com.example.paymentservice.models.Payment;

public interface PaymentGatewayStrategy {
    String getPaymentLink(Payment payment, UserDto user);
}
