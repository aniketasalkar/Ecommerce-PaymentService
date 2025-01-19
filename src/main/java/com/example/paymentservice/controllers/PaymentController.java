package com.example.paymentservice.controllers;

import com.example.paymentservice.dtos.InitiatePaymentRequestDto;
import com.example.paymentservice.dtos.InitiatePaymentResponseDto;
import com.example.paymentservice.services.PaymentService;
import com.example.paymentservice.utils.IDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private IDtoMapper dtoMapper;

    @PostMapping("/initiate_payment")
    public ResponseEntity<InitiatePaymentResponseDto> initiatePayment(@RequestBody InitiatePaymentRequestDto initiatePaymentRequestDto) {
        InitiatePaymentResponseDto initiatePaymentResponseDto;

        try {
            initiatePaymentResponseDto = dtoMapper.toInitiatePaymentResponseDto(
                    paymentService.getPaymentLink(dtoMapper.toPayment(initiatePaymentRequestDto), initiatePaymentRequestDto.getUser()));

            return new ResponseEntity<>(initiatePaymentResponseDto, HttpStatus.OK);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
