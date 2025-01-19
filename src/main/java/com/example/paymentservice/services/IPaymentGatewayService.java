package com.example.paymentservice.services;

import com.example.paymentservice.models.PaymentGateway;

public interface IPaymentGatewayService {
    PaymentGateway createPaymentGateway(PaymentGateway paymentGateway);
}
