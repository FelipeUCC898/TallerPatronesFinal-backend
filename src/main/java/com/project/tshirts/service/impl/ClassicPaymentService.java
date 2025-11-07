package com.project.tshirts.service.impl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassicPaymentService {

    private static volatile ClassicPaymentService instance;
    private static final Object lock = new Object();

    private ClassicPaymentService() {
        log.info("ClassicPaymentService singleton instance created");
    }

    public static ClassicPaymentService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ClassicPaymentService();
                }
            }
        }
        return instance;
    }

    public String processPayment(double amount, String method) {
        log.info("Processing payment via ClassicPaymentService: {} using {}", amount, method);
        return "Pago procesado exitosamente por ClassicPaymentService: " + amount + " con " + method;
    }

    public String getPaymentStatus(String transactionId) {
        log.info("Getting payment status for transaction: {}", transactionId);
        return "Estado del pago para transacción: " + transactionId + " - COMPLETADO";
    }

    public String refundPayment(String transactionId) {
        log.info("Refunding payment for transaction: {}", transactionId);
        return "Pago reembolsado exitosamente para transacción: " + transactionId;
    }
}