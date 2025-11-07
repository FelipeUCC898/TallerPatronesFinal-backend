package com.project.tshirts.controller;

import com.project.tshirts.dto.PaymentRequestDto;
import com.project.tshirts.dto.PaymentResponseDto;
import com.project.tshirts.model.OrderEntity;
import com.project.tshirts.service.OrderService;
import com.project.tshirts.service.PaymentService;
import com.project.tshirts.service.impl.ClassicPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payments", description = "API para gestionar pagos")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @Autowired
    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @PostMapping("/process/{orderId}")
    @Operation(summary = "Procesar pago", description = "Procesa el pago para una orden específica usando el servicio Spring")
    public ResponseEntity<PaymentResponseDto> processPayment(
            @PathVariable Long orderId,
            @Valid @RequestBody PaymentRequestDto paymentRequest) {
        log.info("Processing payment for order: {}", orderId);
        
        // Get order details
        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        
        PaymentResponseDto response = paymentService.processPayment(paymentRequest, order);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/classic/process")
    @Operation(summary = "Procesar pago con Singleton clásico", description = "Demuestra el patrón Singleton clásico")
    public ResponseEntity<String> processPaymentClassic(@RequestParam double amount, @RequestParam String method) {
        log.info("Processing payment with classic singleton");
        ClassicPaymentService classicService = ClassicPaymentService.getInstance();
        String result = classicService.processPayment(amount, method);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{paymentId}")
    @Operation(summary = "Obtener estado de pago", description = "Obtiene el estado de un pago específico")
    public ResponseEntity<PaymentResponseDto> getPaymentStatus(@PathVariable Long paymentId) {
        log.info("Getting payment status for: {}", paymentId);
        PaymentResponseDto response = paymentService.getPaymentStatus(paymentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund/{paymentId}")
    @Operation(summary = "Reembolsar pago", description = "Procesa el reembolso de un pago específico")
    public ResponseEntity<PaymentResponseDto> refundPayment(@PathVariable Long paymentId) {
        log.info("Processing refund for payment: {}", paymentId);
        PaymentResponseDto response = paymentService.refundPayment(paymentId);
        return ResponseEntity.ok(response);
    }
}