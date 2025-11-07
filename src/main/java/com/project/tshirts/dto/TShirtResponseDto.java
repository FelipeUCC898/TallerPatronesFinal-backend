package com.project.tshirts.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class TShirtResponseDto {
    
    private Long id;
    private String size;
    private String color;
    private String print;
    private String fabric;
    private BigDecimal price;
    private String sku;
    private String category;

    public TShirtResponseDto() {}

    public TShirtResponseDto(Long id, String size, String color, String print, String fabric, BigDecimal price, String sku, String category) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.print = print;
        this.fabric = fabric;
        this.price = price;
        this.sku = sku;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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