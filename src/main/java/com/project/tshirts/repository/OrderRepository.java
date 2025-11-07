package com.project.tshirts.repository;

import com.project.tshirts.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    
    List<OrderEntity> findByCustomerEmail(String customerEmail);
    
    List<OrderEntity> findByStatus(OrderEntity.OrderStatus status);
    
    List<OrderEntity> findByCustomerEmailAndStatus(String customerEmail, OrderEntity.OrderStatus status);
}