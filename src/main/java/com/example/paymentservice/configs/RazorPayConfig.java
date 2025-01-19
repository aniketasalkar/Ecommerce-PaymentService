package com.example.paymentservice.configs;

import com.example.paymentservice.exceptions.RazorPayException;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {

    @Value("${razorpay.key.id}")
    private String razorPayId;

    @Value("${razorpay.key.secret}")
    private String razorPaySecret;

    @Bean
    public RazorpayClient getRazorpayClient() {
        try {
            return new RazorpayClient(razorPayId, razorPaySecret);
        } catch (RazorpayException exception) {
            throw new RazorPayException(exception.getMessage());
        }
    }
}
