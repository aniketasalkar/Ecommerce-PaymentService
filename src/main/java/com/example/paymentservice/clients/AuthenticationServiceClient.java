package com.example.paymentservice.clients;

import com.example.paymentservice.dtos.ServiceRegistryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("AUTHENTICATIONSERVICE")
public interface AuthenticationServiceClient {
    @GetMapping("/api/auth/service/fetch_token/{serviceName}")
    ResponseEntity<ServiceRegistryResponseDto> fetchServiceRegistryToken(@PathVariable String serviceName);
}
