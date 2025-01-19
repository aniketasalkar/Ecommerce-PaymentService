package com.example.paymentservice.services;

import com.example.paymentservice.clients.OrderManagementServiceClient;
import com.example.paymentservice.dtos.UpdateOrderDto;
import com.example.paymentservice.exceptions.PaymentNotFoundException;
import com.example.paymentservice.models.Payment;
import com.example.paymentservice.models.PaymentEvents;
import com.example.paymentservice.models.PaymentMode;
import com.example.paymentservice.models.PaymentStatus;
import com.example.paymentservice.repositories.PaymentRepository;
import com.example.paymentservice.utils.TokenValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class RazorpayWebhookService implements IWebhookService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderManagementServiceClient orderManagementServiceClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenValidation tokenValidation;

    @Override
    public void updatePaymentStatus(String paymentStatus) {
        log.info(paymentStatus);
        Map<String, Object> responseMap = null;
        try {
            responseMap = (Map<String, Object>) objectMapper.readValue(paymentStatus, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("responseMap: {}", responseMap);
        Map<String, Object> payloadMap = (Map<String, Object>) responseMap.get("payload");
        log.info("payloadMap: {}", payloadMap);
        Map<String, Object> paymentMap = (Map<String, Object>) payloadMap.get("payment");
        log.info("paymentMap: {}", paymentMap);
        Map<String, Object> paymentLinkMap = (Map<String, Object>) payloadMap.get("payment_link");
        log.info("paymentLinkMap: {}", paymentLinkMap);

        PaymentMode paymentMode = null;
        String transactionId = "";
        if (paymentMap != null) {
            paymentMode = extractPaymentMode(paymentMap);
            log.info("paymentMode: {}", paymentMode);
            transactionId = extractTransactionId(paymentMap, paymentMode);
            log.info("transactionId: {}", transactionId);
        }
        log.info("extracting order id");
        String orderId = extractOrderId(paymentLinkMap);
        log.info("orderId: {}", orderId);

        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() ->
                new PaymentNotFoundException("Payment Not found"));
        if (paymentMode != null) {
            payment.setPaymentMode(paymentMode);
        }
        payment.setGatewayTransactionId(transactionId);

        PaymentEvents event = getPaymentEvent((String) responseMap.get("event"));
        log.info("event: {}", event);
        setPaymentStatus(payment, event);

        paymentRepository.save(payment);

        updateOrder(orderId, event, payment);
    }

    private void updateOrder(String orderId, PaymentEvents event, Payment payment) {
        UpdateOrderDto updateOrderDto = new UpdateOrderDto();
        updateOrderDto.setOrderId(orderId);
        updateOrderDto.setUserId(payment.getUserId());
        if (event == PaymentEvents.PAYMENT_LINK_PAID) {
            updateOrderDto.setPaymentStatus(PaymentStatus.COMPLETED.toString());
        } else if (event == PaymentEvents.PAYMENT_LINK_CANCELLED || event == PaymentEvents.PAYMENT_LINK_FAILED) {
            updateOrderDto.setPaymentStatus(PaymentStatus.FAILED.toString());
        }

        orderManagementServiceClient.updateOrder(payment.getOrderNumber(), updateOrderDto);
    }

    private PaymentEvents getPaymentEvent(String event) {
        PaymentEvents paymentEvent = null;
        switch (event) {
            case "payment_link.paid":
                paymentEvent = PaymentEvents.PAYMENT_LINK_PAID;
                break;
            case "payment_link.expired":
                paymentEvent = PaymentEvents.PAYMENT_LINK_FAILED;
                break;
            case "payment_link.cancelled":
                paymentEvent = PaymentEvents.PAYMENT_LINK_CANCELLED;
                break;
        }
        return paymentEvent;
    }

    private PaymentMode getPaymentMode(String paymentMode) {
        PaymentMode paymentModeEnum = null;
        switch (paymentMode) {
            case "card":
                paymentModeEnum = PaymentMode.CREDIT_CARD;
                break;
            case "netbanking":
                paymentModeEnum = PaymentMode.NET_BANKING;
                break;
            case "upi":
                paymentModeEnum = PaymentMode.UPI;
                break;
        }

        return paymentModeEnum;
    }

    private String extractOrderId(Map<String, Object> paymentLinkMap) {
        return (String) ((Map<String, Object>) paymentLinkMap.get("entity")).get("reference_id");
    }

    private PaymentMode extractPaymentMode(Map<String, Object> paymentMap) {
        return getPaymentMode((String) ((Map<String, Object>) paymentMap.get("entity")).get("method"));
    }

    private String extractTransactionId(Map<String, Object> paymentMap, PaymentMode paymentMode) {
        String transactionId = "";
        if (paymentMode == PaymentMode.UPI || paymentMode == PaymentMode.CREDIT_CARD || paymentMode == PaymentMode.DEBIT_CARD) {
            transactionId = (String) ((Map<String, Object>) ((Map<String, Object>) paymentMap.get("entity")).get("acquirer_data")).get("rrn");
        } else if (paymentMode == PaymentMode.NET_BANKING) {
            transactionId = (String) ((Map<String, Object>) ((Map<String, Object>) paymentMap.get("entity")).get("acquirer_data")).get("bank_transaction_id");
        }

        return transactionId;
    }

    private void setPaymentStatus(Payment payment, PaymentEvents event) {
        if (event == PaymentEvents.PAYMENT_LINK_CANCELLED || event == PaymentEvents.PAYMENT_LINK_FAILED) {
            payment.setStatus(PaymentStatus.FAILED);
        } else if (event == PaymentEvents.PAYMENT_LINK_PAID){
            payment.setStatus(PaymentStatus.COMPLETED);
        }
    }
}
