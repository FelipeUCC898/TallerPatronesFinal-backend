package com.project.tshirts.service.impl;

import com.project.tshirts.dto.PaymentRequestDto;
import com.project.tshirts.dto.PaymentResponseDto;
import com.project.tshirts.model.OrderEntity;
import com.project.tshirts.model.PaymentEntity;
import com.project.tshirts.repository.PaymentRepository;
import com.project.tshirts.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequest, OrderEntity order) {
        log.info("Processing payment for order: {}", order.getId());
        
        try {
            // Simulate payment processing
            String transactionId = UUID.randomUUID().toString();
            
            PaymentEntity payment = new PaymentEntity();
            payment.setOrder(order);
            payment.setAmount(paymentRequest.getAmount());
            payment.setPaymentMethod(paymentRequest.getPaymentMethod());
            payment.setTransactionId(transactionId);
            payment.setStatus(PaymentEntity.PaymentStatus.COMPLETED);
            payment.setPaymentDetails(paymentRequest.getPaymentDetails());
            
            PaymentEntity savedPayment = paymentRepository.save(payment);
            
            log.info("Payment processed successfully: {}", savedPayment.getId());
            
            return convertToDto(savedPayment, "Pago procesado exitosamente");
            
        } catch (Exception e) {
            log.error("Error processing payment: {}", e.getMessage());
            
            PaymentEntity failedPayment = new PaymentEntity();
            failedPayment.setOrder(order);
            failedPayment.setAmount(paymentRequest.getAmount());
            failedPayment.setPaymentMethod(paymentRequest.getPaymentMethod());
            failedPayment.setStatus(PaymentEntity.PaymentStatus.FAILED);
            failedPayment.setPaymentDetails("Error: " + e.getMessage());
            
            PaymentEntity savedPayment = paymentRepository.save(failedPayment);
            
            return convertToDto(savedPayment, "Error al procesar el pago");
        }
    }

    @Override
    public PaymentResponseDto getPaymentStatus(Long paymentId) {
        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        
        return convertToDto(payment, "Estado del pago");
    }

    @Override
    public PaymentResponseDto refundPayment(Long paymentId) {
        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        
        if (payment.getStatus() != PaymentEntity.PaymentStatus.COMPLETED) {
            return convertToDto(payment, "Solo se pueden reembolsar pagos completados");
        }
        
        payment.setStatus(PaymentEntity.PaymentStatus.REFUNDED);
        PaymentEntity updatedPayment = paymentRepository.save(payment);
        
        log.info("Payment refunded: {}", paymentId);
        
        return convertToDto(updatedPayment, "Pago reembolsado exitosamente");
    }

    private PaymentResponseDto convertToDto(PaymentEntity payment, String message) {
        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setId(payment.getId());
        dto.setOrderId(payment.getOrder().getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setTransactionId(payment.getTransactionId());
        dto.setStatus(payment.getStatus().toString());
        dto.setMessage(message);
        dto.setTimestamp(LocalDateTime.now());
        return dto;
    }
}