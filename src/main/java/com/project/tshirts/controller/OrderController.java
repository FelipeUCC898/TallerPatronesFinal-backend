package com.project.tshirts.controller;

import com.project.tshirts.dto.OrderRequestDto;
import com.project.tshirts.dto.OrderResponseDto;
import com.project.tshirts.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "API para gestionar órdenes de camisetas")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Crear una orden", description = "Crea una nueva orden con camisetas personalizadas")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequest) {
        log.info("Creating order for customer: {}", orderRequest.getCustomerEmail());
        OrderResponseDto response = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por ID", description = "Obtiene los detalles de una orden específica")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        log.info("Getting order by ID: {}", id);
        OrderResponseDto response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las órdenes", description = "Obtiene una lista de todas las órdenes")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        log.info("Getting all orders");
        List<OrderResponseDto> response = orderService.getAllOrders();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{email}")
    @Operation(summary = "Obtener órdenes por email", description = "Obtiene todas las órdenes de un cliente específico")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByCustomerEmail(@PathVariable String email) {
        log.info("Getting orders by customer email: {}", email);
        List<OrderResponseDto> response = orderService.getOrdersByCustomerEmail(email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Actualizar estado de orden", description = "Actualiza el estado de una orden específica")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long id, 
            @RequestParam String status) {
        log.info("Updating order {} status to: {}", id, status);
        OrderResponseDto response = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(response);
    }
}