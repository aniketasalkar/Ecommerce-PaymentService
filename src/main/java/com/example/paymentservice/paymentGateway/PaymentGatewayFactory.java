package com.example.paymentservice.paymentGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentGatewayFactory {
    private final Map<String, PaymentGatewayStrategy> gateways;

    @Autowired
    public PaymentGatewayFactory(List<PaymentGatewayStrategy> gatewayStrategies) {
        gateways = new HashMap<>();
        for (PaymentGatewayStrategy gateway : gatewayStrategies) {
            if (gateway instanceof RazorpayPaymentGateway) {
                gateways.put("RAZORPAY", gateway);
            }
        }
    }

    public PaymentGatewayStrategy getGateway(String gatewayName) {
        return gateways.getOrDefault(gatewayName.toUpperCase(), null);
    }
}
