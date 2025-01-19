package com.example.paymentservice.repositories;

import com.example.paymentservice.models.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
//    Optional<TransactionLog> findByTransactionId(Long transactionId);
}
