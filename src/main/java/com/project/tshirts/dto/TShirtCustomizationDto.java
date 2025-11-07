package com.project.tshirts.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class TShirtCustomizationDto {
    
    @NotNull(message = "El tamaño es obligatorio")
    @Pattern(regexp = "^[SMLX]{1,2}$", message = "El tamaño debe ser S, M, L o XL")
    private String size;
    
    @NotBlank(message = "El color es obligatorio")
    @Size(min = 2, max = 50, message = "El color debe tener entre 2 y 50 caracteres")
    private String color;
    
    @NotBlank(message = "El diseño es obligatorio")
    @Size(min = 2, max = 100, message = "El diseño debe tener entre 2 y 100 caracteres")
    private String print;
    
    @NotNull(message = "La tela es obligatoria")
    @Pattern(regexp = "^(cotton|polyester|blend)$", message = "La tela debe ser cotton, polyester o blend")
    private String fabric;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Digits(integer = 6, fraction = 2, message = "El precio debe tener máximo 6 dígitos enteros y 2 decimales")
    private BigDecimal price;
    
    @NotBlank(message = "El SKU es obligatorio")
    @Size(min = 3, max = 50, message = "El SKU debe tener entre 3 y 50 caracteres")
    private String sku;
    
    @NotBlank(message = "La categoría es obligatoria")
    @Pattern(regexp = "^(casual|sport|premium)$", message = "La categoría debe ser casual, sport o premium")
    private String category;

    public TShirtCustomizationDto() {}

    public TShirtCustomizationDto(String size, String color, String print, String fabric, BigDecimal price, String sku, String category) {
        this.size = size;
        this.color = color;
        this.print = print;
        this.fabric = fabric;
        this.price = price;
        this.sku = sku;
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}