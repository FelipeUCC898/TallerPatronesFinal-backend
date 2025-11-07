package com.project.tshirts.dto;

import java.math.BigDecimal;

public class OrderItemResponseDto {
    
    private Long id;
    private TShirtResponseDto tshirt;
    private Integer quantity;
    private BigDecimal subtotal;

    public OrderItemResponseDto() {}

    public OrderItemResponseDto(Long id, TShirtResponseDto tshirt, Integer quantity, BigDecimal subtotal) {
        this.id = id;
        this.tshirt = tshirt;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TShirtResponseDto getTshirt() {
        return tshirt;
    }

    public void setTshirt(TShirtResponseDto tshirt) {
        this.tshirt = tshirt;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}