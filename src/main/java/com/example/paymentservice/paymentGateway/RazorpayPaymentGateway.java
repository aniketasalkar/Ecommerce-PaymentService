package com.example.paymentservice.paymentGateway;

import com.example.paymentservice.dtos.UserDto;
import com.example.paymentservice.exceptions.RazorPayException;
import com.example.paymentservice.models.Payment;
import com.razorpay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.json.JSONObject;
import com.razorpay.RazorpayClient;

@Component
public class RazorpayPaymentGateway implements PaymentGatewayStrategy {

    @Autowired
    private RazorpayClient razorpayClient;

    @Override
    public String getPaymentLink(Payment payment, UserDto user) {
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", payment.getAmount() * 100);
            paymentLinkRequest.put("currency", payment.getCurrency().toString());
//            paymentLinkRequest.put("accept_partial",true);
//            paymentLinkRequest.put("first_min_partial_amount",100);
            paymentLinkRequest.put("expire_by", payment.getExpiry());
            paymentLinkRequest.put("reference_id", payment.getOrderId());
            paymentLinkRequest.put("description", "Payment for order no" + payment.getOrderId());
            JSONObject customer = new JSONObject();
            customer.put("name", user.getName());
            customer.put("contact", "+91" + user.getPhoneNumber());
            customer.put("email", user.getEmail());
            paymentLinkRequest.put("customer",customer);
            JSONObject notify = new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);
            paymentLinkRequest.put("reminder_enable",true);
//            JSONObject notes = new JSONObject();
//            notes.put("policy_name","Jeevan Bima");
//            paymentLinkRequest.put("notes",notes);
//            paymentLinkRequest.put("callback_url","https://amazon.in/");
            paymentLinkRequest.put("callback_url","http://localhost:8086/api/orders/orders/tracking/" + payment.getOrderId());
            paymentLinkRequest.put("callback_method","get");

            PaymentLink paymentResponse = razorpayClient.paymentLink.create(paymentLinkRequest);

            return paymentResponse.get("short_url").toString();
        } catch (RazorpayException e) {
            throw new RazorPayException(e.getMessage());
        }
    }
}
