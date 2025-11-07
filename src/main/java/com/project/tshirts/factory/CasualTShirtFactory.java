package com.project.tshirts.factory;

import com.project.tshirts.builder.TShirt;
import com.project.tshirts.dto.TShirtCustomizationDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CasualTShirtFactory implements TShirtFactory {

    @Override
    public TShirt createTShirt(TShirtCustomizationDto customization) {
        // Handle null customization with default values
        if (customization == null) {
            String uniqueSku = "CAS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            return new TShirt.TShirtBuilder()
                    .size("M")
                    .color("White")
                    .print("Casual: Default")
                    .fabric("cotton")
                    .price(new BigDecimal("25.00"))
                    .sku(uniqueSku)
                    .build();
        }
        
        BigDecimal finalPrice = calculatePrice(customization);
        String baseSku = customization.getSku() != null ? customization.getSku() : "DEFAULT";
        String uniqueSku = "CAS-" + baseSku + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        return new TShirt.TShirtBuilder()
                .size(customization.getSize())
                .color(customization.getColor())
                .print("Casual: " + customization.getPrint())
                .fabric(customization.getFabric())
                .price(finalPrice)
                .sku(uniqueSku)
                .build();
    }

    @Override
    public String getCategory() {
        return "casual";
    }

    private BigDecimal calculatePrice(TShirtCustomizationDto customization) {
        if (customization == null || customization.getPrice() == null) {
            return new BigDecimal("25.00");
        }
        
        BigDecimal basePrice = customization.getPrice();
        String fabric = customization.getFabric() != null ? customization.getFabric().toLowerCase() : "cotton";
        
        switch (fabric) {
            case "cotton":
                return basePrice.multiply(BigDecimal.valueOf(1.0));
            case "polyester":
                return basePrice.multiply(BigDecimal.valueOf(0.9));
            case "blend":
                return basePrice.multiply(BigDecimal.valueOf(0.95));
            default:
                return basePrice;
        }
    }
}