package com.project.tshirts.service.impl;

import com.project.tshirts.dto.OrderItemDto;
import com.project.tshirts.dto.OrderRequestDto;
import com.project.tshirts.dto.OrderResponseDto;
import com.project.tshirts.dto.OrderItemResponseDto;
import com.project.tshirts.dto.TShirtResponseDto;
import com.project.tshirts.model.OrderEntity;
import com.project.tshirts.model.OrderItemEntity;
import com.project.tshirts.model.TShirtEntity;
import com.project.tshirts.repository.OrderItemRepository;
import com.project.tshirts.repository.OrderRepository;
import com.project.tshirts.repository.TShirtRepository;
import com.project.tshirts.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final TShirtRepository tshirtRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, 
                            OrderItemRepository orderItemRepository,
                            TShirtRepository tshirtRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.tshirtRepository = tshirtRepository;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        log.info("Creating order for customer: {}", orderRequest.getCustomerEmail());
        
        // Create order entity
        OrderEntity order = new OrderEntity();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setTotalAmount(BigDecimal.ZERO);
        order.setStatus(OrderEntity.OrderStatus.PENDING);
        
        OrderEntity savedOrder = orderRepository.save(order);
        
        // Process order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderItemDto itemDto : orderRequest.getItems()) {
            TShirtEntity tshirt = tshirtRepository.findById(itemDto.getTshirtId())
                    .orElseThrow(() -> new RuntimeException("TShirt no encontrado con ID: " + itemDto.getTshirtId()));
            
            BigDecimal subtotal = tshirt.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalAmount = totalAmount.add(subtotal);
            
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(savedOrder);
            orderItem.setTshirt(tshirt);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setSubtotal(subtotal);
            
            orderItemRepository.save(orderItem);
        }
        
        // Update order total
        savedOrder.setTotalAmount(totalAmount);
        orderRepository.save(savedOrder);
        
        log.info("Order created successfully with ID: {}", savedOrder.getId());
        return convertToDto(savedOrder);
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        return convertToDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long id, String status) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        
        try {
            OrderEntity.OrderStatus newStatus = OrderEntity.OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
            OrderEntity updatedOrder = orderRepository.save(order);
            
            log.info("Order {} status updated to: {}", id, status);
            return convertToDto(updatedOrder);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado de orden no válido: " + status);
        }
    }

    private OrderResponseDto convertToDto(OrderEntity entity) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(entity.getId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCustomerEmail(entity.getCustomerEmail());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setStatus(entity.getStatus().toString());
        dto.setCreatedAt(entity.getCreatedAt());
        
        List<OrderItemResponseDto> items = orderItemRepository.findByOrderId(entity.getId()).stream()
                .map(this::convertItemToDto)
                .collect(Collectors.toList());
        dto.setItems(items);
        
        return dto;
    }

    private OrderItemResponseDto convertItemToDto(OrderItemEntity item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setId(item.getId());
        
        // Convert TShirtEntity to TShirtResponseDto
        TShirtResponseDto tshirtDto = new TShirtResponseDto();
        tshirtDto.setId(item.getTshirt().getId());
        tshirtDto.setSize(item.getTshirt().getSize());
        tshirtDto.setColor(item.getTshirt().getColor());
        tshirtDto.setPrint(item.getTshirt().getPrint());
        tshirtDto.setFabric(item.getTshirt().getFabric());
        tshirtDto.setPrice(item.getTshirt().getPrice());
        tshirtDto.setSku(item.getTshirt().getSku());
        tshirtDto.setCategory(item.getTshirt().getCategory());
        
        dto.setTshirt(tshirtDto);
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }
}