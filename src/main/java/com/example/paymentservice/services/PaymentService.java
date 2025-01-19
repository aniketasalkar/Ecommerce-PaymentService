package com.example.paymentservice.services;

import com.example.paymentservice.dtos.UserDto;
import com.example.paymentservice.exceptions.RazorPayException;
import com.example.paymentservice.models.Payment;
import com.example.paymentservice.models.PaymentStatus;
import com.example.paymentservice.paymentGateway.PaymentGatewayFactory;
import com.example.paymentservice.paymentGateway.PaymentGatewayStrategy;
import com.example.paymentservice.repositories.PaymentGatewayRepository;
import com.example.paymentservice.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    PaymentGatewayFactory paymentGatewayFactory;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentGatewayRepository paymentGatewayRepository;

    @Autowired
    private PaymentGatewayStrategy paymentGatewayStrategy;


    @Override
    public Payment getPaymentLink(Payment payment, UserDto user) {
        Calendar now = Calendar.getInstance();

        payment.setCreatedAt(now.getTime());
        payment.setUpdatedAt(now.getTime());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setUserId(user.getUserId());

        now.add(Calendar.MINUTE, 16);
        payment.setExpiry(now.getTimeInMillis() / 1000);

        paymentGatewayStrategy = paymentGatewayFactory.getGateway(payment.getPreferredPaymentGateway().toString());
        try {
            String link = paymentGatewayStrategy.getPaymentLink(payment, user);
            payment.setPaymentLink(link);
        } catch (Exception exception) {
            throw new RazorPayException(exception.getMessage());
        }

        return paymentRepository.save(payment);
    }
}
