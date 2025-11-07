package com.project.tshirts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public class OrderRequestDto {
    
    @NotEmpty(message = "La orden debe contener al menos un item")
    private List<@Valid OrderItemDto> items;
    
    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String customerName;
    
    @NotBlank(message = "El email del cliente es obligatorio")
    @Email(message = "El email debe ser válido")
    private String customerEmail;

    public OrderRequestDto() {}

    public OrderRequestDto(List<OrderItemDto> items, String customerName, String customerEmail) {
        this.items = items;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}