package com.example.paymentservice.dtos;

import com.example.paymentservice.models.Currency;
import com.example.paymentservice.models.PaymentGateway;
import com.example.paymentservice.models.PaymentMode;
import com.example.paymentservice.models.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InitiatePaymentRequestDto {
    @NotNull(message = "orderNumber cannot be null and should be of type Long")
    private Long orderNumber;

    @NotBlank(message = "orderId cannot be null")
    private String orderId;

    @NotNull(message = "amount cannot be null")
    @Min(value = 1)
//    @Max(value = 10000000)
    private Double amount;

    private String currency;

    private String paymentGateway;

    @NotBlank(message = "paymentMode cannot be blank")
    private String paymentMode;

    @NotNull(message = "need user details")
    private UserDto user;
}
