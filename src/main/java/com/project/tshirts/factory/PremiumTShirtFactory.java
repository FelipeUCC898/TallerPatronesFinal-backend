package com.project.tshirts.factory;

import com.project.tshirts.builder.TShirt;
import com.project.tshirts.dto.TShirtCustomizationDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class PremiumTShirtFactory implements TShirtFactory {

    @Override
    public TShirt createTShirt(TShirtCustomizationDto customization) {
        // Handle null customization with default values
        if (customization == null) {
            String uniqueSku = "PREMIUM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            return new TShirt.TShirtBuilder()
                    .size("M")
                    .color("White")
                    .print("Premium: Default")
                    .fabric("cotton")
                    .price(new BigDecimal("50.00"))
                    .sku(uniqueSku)
                    .build();
        }
        
        BigDecimal finalPrice = calculatePrice(customization);
        String baseSku = customization.getSku() != null ? customization.getSku() : "DEFAULT";
        String uniqueSku = "PREMIUM-" + baseSku + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        return new TShirt.TShirtBuilder()
                .size(customization.getSize())
                .color(customization.getColor())
                .print("Premium: " + customization.getPrint())
                .fabric("cotton") // Premium shirts always use cotton
                .price(finalPrice)
                .sku(uniqueSku)
                .build();
    }

    @Override
    public String getCategory() {
        return "premium";
    }

    private BigDecimal calculatePrice(TShirtCustomizationDto customization) {
        if (customization == null || customization.getPrice() == null) {
            return new BigDecimal("50.00");
        }
        
        BigDecimal basePrice = customization.getPrice();
        
        // Premium shirts have a significant markup
        return basePrice.multiply(BigDecimal.valueOf(1.5));
    }
}