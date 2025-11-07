package com.project.tshirts.service;

import com.project.tshirts.dto.OrderRequestDto;
import com.project.tshirts.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequest);
    OrderResponseDto getOrderById(Long id);
    List<OrderResponseDto> getAllOrders();
    List<OrderResponseDto> getOrdersByCustomerEmail(String email);
    OrderResponseDto updateOrderStatus(Long id, String status);
}