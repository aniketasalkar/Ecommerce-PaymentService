package com.example.paymentservice.dtos;

import com.example.paymentservice.models.GatewayStatus;
import com.example.paymentservice.models.Gateways;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class PaymentGatewayDto {
    @NotBlank(message = "payment Gateway cannot be blank")
    private String paymentGateway;

//    @Column(nullable = false)
//    private GatewayStatus status;

    @NotNull(message = "transaction fee cannot be null")
    private float transactionFee;
}
