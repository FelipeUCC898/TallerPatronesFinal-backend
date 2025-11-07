package com.project.tshirts.service;

import com.project.tshirts.dto.PaymentRequestDto;
import com.project.tshirts.dto.PaymentResponseDto;
import com.project.tshirts.model.OrderEntity;
import com.project.tshirts.model.PaymentEntity;

public interface PaymentService {
    PaymentResponseDto processPayment(PaymentRequestDto paymentRequest, OrderEntity order);
    PaymentResponseDto getPaymentStatus(Long paymentId);
    PaymentResponseDto refundPayment(Long paymentId);
}