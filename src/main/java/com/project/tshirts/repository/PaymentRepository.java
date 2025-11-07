package com.project.tshirts.repository;

import com.project.tshirts.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    
    Optional<PaymentEntity> findByOrderId(Long orderId);
    
    Optional<PaymentEntity> findByTransactionId(String transactionId);
    
    List<PaymentEntity> findByStatus(PaymentEntity.PaymentStatus status);
    
    List<PaymentEntity> findByPaymentMethod(String paymentMethod);
}