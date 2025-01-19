package com.example.paymentservice.repositories;

import com.example.paymentservice.models.Gateways;
import com.example.paymentservice.models.PaymentGateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentGatewayRepository extends JpaRepository<PaymentGateway, Long> {
    Optional<PaymentGateway> findByPaymentGateway(Gateways paymentGateway);
}
