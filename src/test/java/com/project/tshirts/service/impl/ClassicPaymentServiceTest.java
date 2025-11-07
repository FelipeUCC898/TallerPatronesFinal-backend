package com.project.tshirts.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ClassicPaymentServiceTest {

    @Test
    @DisplayName("Should implement Singleton pattern - only one instance exists")
    public void testSingletonPattern() {
        ClassicPaymentService instance1 = ClassicPaymentService.getInstance();
        ClassicPaymentService instance2 = ClassicPaymentService.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2);
        assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    @DisplayName("Should process payment successfully")
    public void testProcessPayment() {
        ClassicPaymentService service = ClassicPaymentService.getInstance();
        
        String result = service.processPayment(99.99, "CREDIT_CARD");
        
        assertNotNull(result);
        assertTrue(result.contains("99.99"));
        assertTrue(result.contains("CREDIT_CARD"));
    }

    @Test
    @DisplayName("Should get payment status")
    public void testGetPaymentStatus() {
        ClassicPaymentService service = ClassicPaymentService.getInstance();
        
        String status = service.getPaymentStatus("TRANS-123");
        
        assertNotNull(status);
        assertTrue(status.contains("COMPLETADO"));
    }

    @Test
    @DisplayName("Should refund payment successfully")
    public void testRefundPayment() {
        ClassicPaymentService service = ClassicPaymentService.getInstance();
        
        String result = service.refundPayment("TRANS-123");
        
        assertNotNull(result);
        assertTrue(result.contains("TRANS-123"));
        assertTrue(result.contains("reembolsado"));
    }

    @Test
    @DisplayName("Should handle concurrent access to singleton")
    public void testConcurrentSingletonAccess() throws InterruptedException {
        final ClassicPaymentService[] instances = new ClassicPaymentService[10];
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instances[index] = ClassicPaymentService.getInstance();
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        ClassicPaymentService firstInstance = instances[0];
        for (int i = 1; i < 10; i++) {
            assertSame(firstInstance, instances[i], "All instances should be the same");
        }
    }

    @Test
    @DisplayName("Should maintain state across multiple calls")
    public void testStateMaintenance() {
        ClassicPaymentService service1 = ClassicPaymentService.getInstance();
        ClassicPaymentService service2 = ClassicPaymentService.getInstance();
        
        service1.processPayment(50.00, "DEBIT_CARD");
        
        String status1 = service1.getPaymentStatus("TRANS-456");
        String status2 = service2.getPaymentStatus("TRANS-456");
        
        assertEquals(status1, status2);
    }

    @Test
    @DisplayName("Should handle edge cases in payment processing")
    public void testEdgeCases() {
        ClassicPaymentService service = ClassicPaymentService.getInstance();
        
        String result1 = service.processPayment(0.01, "CASH");
        assertNotNull(result1);
        
        String result2 = service.processPayment(9999.99, "CREDIT_CARD");
        assertNotNull(result2);
        
        String result3 = service.refundPayment("TRANS-003");
        assertNotNull(result3);
    }
}