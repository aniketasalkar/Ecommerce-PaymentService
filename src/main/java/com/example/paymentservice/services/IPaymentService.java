package com.example.paymentservice.services;

import com.example.paymentservice.dtos.UserDto;
import com.example.paymentservice.models.Payment;

public interface IPaymentService {
    Payment getPaymentLink(Payment payment, UserDto user);
}
