package com.example.paymentservice.controllers;

import com.example.paymentservice.dtos.PaymentGatewayDto;
import com.example.paymentservice.services.PaymentGatewayService;
import com.example.paymentservice.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment/gateway")
public class PaymentGatewayController {

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @Autowired
    private DtoMapper dtoMapper;

    @PostMapping("/add")
    public ResponseEntity<PaymentGatewayDto> addPaymentGateway(@RequestBody PaymentGatewayDto paymentGatewayRequestDto) {
        PaymentGatewayDto paymentGatewayResponseDto;

        try {
            paymentGatewayResponseDto = dtoMapper.toPaymentGatewayDto(
                    paymentGatewayService.createPaymentGateway(dtoMapper.toPaymentGateway(paymentGatewayRequestDto)));

            return new ResponseEntity<>(paymentGatewayResponseDto, HttpStatus.CREATED);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
