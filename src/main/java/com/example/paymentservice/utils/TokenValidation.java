package com.example.paymentservice.utils;

import com.example.paymentservice.clients.AuthenticationServiceClient;
import com.example.paymentservice.dtos.ServiceRegistryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenValidation {

    @Autowired
    AuthenticationServiceClient authenticationServiceClient;

    public ServiceRegistryResponseDto fetchToken(String serviceName) {
       return authenticationServiceClient.fetchServiceRegistryToken(serviceName).getBody();
    }
}
