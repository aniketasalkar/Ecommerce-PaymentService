package com.example.paymentservice.utils;

import com.example.paymentservice.dtos.InitiatePaymentRequestDto;
import com.example.paymentservice.dtos.InitiatePaymentResponseDto;
import com.example.paymentservice.dtos.PaymentGatewayDto;
import com.example.paymentservice.models.Payment;
import com.example.paymentservice.models.PaymentGateway;

public interface IDtoMapper {
    Payment toPayment(InitiatePaymentRequestDto initiatePaymentRequestDto);
    InitiatePaymentResponseDto toInitiatePaymentResponseDto(Payment payment);
    PaymentGateway toPaymentGateway(PaymentGatewayDto paymentGatewayDto);
    PaymentGatewayDto toPaymentGatewayDto(PaymentGateway paymentGateway);
}
