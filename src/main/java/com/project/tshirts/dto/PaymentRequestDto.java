package com.project.tshirts.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PaymentRequestDto {
    
    @NotNull(message = "El ID de la orden es obligatorio")
    private Long orderId;
    
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El monto debe tener máximo 8 dígitos enteros y 2 decimales")
    private BigDecimal amount;
    
    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(regexp = "^(credit_card|debit_card|paypal)$", message = "El método de pago debe ser credit_card, debit_card o paypal")
    private String paymentMethod;
    
    @NotBlank(message = "Los detalles del pago son obligatorios")
    private String paymentDetails;

    public PaymentRequestDto() {}

    public PaymentRequestDto(Long orderId, BigDecimal amount, String paymentMethod, String paymentDetails) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}