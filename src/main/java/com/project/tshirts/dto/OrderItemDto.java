package com.project.tshirts.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class OrderItemDto {
    
    @NotNull(message = "El ID de la camiseta es obligatorio")
    private Long tshirtId;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Max(value = 100, message = "La cantidad no puede exceder 100")
    private Integer quantity;

    public OrderItemDto() {}

    public OrderItemDto(Long tshirtId, Integer quantity) {
        this.tshirtId = tshirtId;
        this.quantity = quantity;
    }

    public Long getTshirtId() {
        return tshirtId;
    }

    public void setTshirtId(Long tshirtId) {
        this.tshirtId = tshirtId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}