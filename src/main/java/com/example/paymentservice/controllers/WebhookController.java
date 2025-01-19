package com.example.paymentservice.controllers;

import com.example.paymentservice.services.IWebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment/webhook")
public class WebhookController {

    @Autowired
    private IWebhookService webhookService;

    @PostMapping("/payment_event")
    public ResponseEntity<Void> paymentEventWebhook(@RequestBody String WebhookEvent) {
        try {
            webhookService.updatePaymentStatus(WebhookEvent);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
