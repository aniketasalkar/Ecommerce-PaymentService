package com.example.paymentservice.services;

import com.example.paymentservice.exceptions.AlreadyExistsException;
import com.example.paymentservice.models.GatewayStatus;
import com.example.paymentservice.models.PaymentGateway;
import com.example.paymentservice.paymentGateway.PaymentGatewayFactory;
import com.example.paymentservice.repositories.PaymentGatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentGatewayService implements IPaymentGatewayService {

    @Autowired
    private PaymentGatewayRepository paymentGatewayRepository;

    @Autowired
    private PaymentGatewayFactory paymentGatewayFactory;

    @Override
    public PaymentGateway createPaymentGateway(PaymentGateway paymentGateway) {
        if (!paymentGatewayRepository.findByPaymentGateway(paymentGateway.getPaymentGateway()).isEmpty()) {
            throw new AlreadyExistsException("Payment gateway already exists");
        }

        Date now = new Date();

        paymentGateway.setCreatedAt(now);
        paymentGateway.setUpdatedAt(now);
        paymentGateway.setStatus(GatewayStatus.ACTIVE);

        return paymentGatewayRepository.save(paymentGateway);
    }
}
