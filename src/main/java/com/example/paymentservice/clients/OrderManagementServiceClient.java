package com.example.paymentservice.clients;

import com.example.paymentservice.dtos.OrderResponseDto;
import com.example.paymentservice.dtos.UpdateOrderDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("ORDERMANAGEMENTSERVICE")
public interface OrderManagementServiceClient {
    @PostMapping("/api/orders/orders/{id}/update_order")
    ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id, @RequestBody @Valid UpdateOrderDto updateOrderDto);
}
