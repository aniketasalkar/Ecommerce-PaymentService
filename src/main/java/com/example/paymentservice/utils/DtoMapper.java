package com.example.paymentservice.utils;

import com.example.paymentservice.dtos.InitiatePaymentRequestDto;
import com.example.paymentservice.dtos.InitiatePaymentResponseDto;
import com.example.paymentservice.dtos.PaymentGatewayDto;
import com.example.paymentservice.exceptions.InvalidRequestException;
import com.example.paymentservice.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DtoMapper implements IDtoMapper {

    @Override
    public Payment toPayment(InitiatePaymentRequestDto initiatePaymentRequestDto) {
        Payment payment = new Payment();
        payment.setAmount(initiatePaymentRequestDto.getAmount());
        payment.setOrderId(initiatePaymentRequestDto.getOrderId());
        payment.setOrderNumber(initiatePaymentRequestDto.getOrderNumber());
        try {
            payment.setPaymentMode(PaymentMode.valueOf(initiatePaymentRequestDto.getPaymentMode().trim().toUpperCase()));
        } catch (Exception exception) {
            throw new InvalidRequestException("Invalid payment mode: " + initiatePaymentRequestDto.getPaymentMode());
        }

        if (initiatePaymentRequestDto.getCurrency() != null) {
            try {
                payment.setCurrency(Currency.valueOf(initiatePaymentRequestDto.getCurrency().trim().toUpperCase()));
            } catch (Exception exception) {
                throw new InvalidRequestException("Invalid currency: " + initiatePaymentRequestDto.getCurrency());
            }
        } else {
            payment.setCurrency(Currency.INR);
        }

        if (initiatePaymentRequestDto.getPaymentGateway() != null) {
            try {
                payment.setPreferredPaymentGateway(Gateways.valueOf(initiatePaymentRequestDto.getPaymentGateway().trim().toUpperCase()));
            } catch (Exception exception) {
                throw new InvalidRequestException("Invalid payment gateway: " + initiatePaymentRequestDto.getPaymentGateway());
            }
        } else {
            payment.setPreferredPaymentGateway(Gateways.RAZORPAY);
        }

        return payment;
    }

    @Override
    public InitiatePaymentResponseDto toInitiatePaymentResponseDto(Payment payment) {
        InitiatePaymentResponseDto initiatePaymentResponseDto = new InitiatePaymentResponseDto();
//        initiatePaymentResponseDto.setOrderId(payment.getOrderId());
        initiatePaymentResponseDto.setPaymentLink(payment.getPaymentLink());
//        initiatePaymentResponseDto.setExpiry(new Date(payment.getExpiry()));

        return initiatePaymentResponseDto;
    }

    @Override
    public PaymentGateway toPaymentGateway(PaymentGatewayDto paymentGatewayDto) {
        PaymentGateway paymentGateway = new PaymentGateway();
        paymentGateway.setTransactionFee(paymentGatewayDto.getTransactionFee());
        try {
            paymentGateway.setPaymentGateway(Gateways.valueOf(paymentGatewayDto.getPaymentGateway().trim().toUpperCase()));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new InvalidRequestException("Invalid payment gateway: " + paymentGatewayDto.getPaymentGateway());
        }

        return paymentGateway;
    }

    @Override
    public PaymentGatewayDto toPaymentGatewayDto(PaymentGateway paymentGateway) {
        PaymentGatewayDto paymentGatewayDto = new PaymentGatewayDto();

        paymentGatewayDto.setTransactionFee(paymentGateway.getTransactionFee());
        paymentGatewayDto.setPaymentGateway(paymentGateway.getPaymentGateway().toString());

        return paymentGatewayDto;
    }
}
