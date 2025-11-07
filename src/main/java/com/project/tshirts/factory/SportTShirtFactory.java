package com.project.tshirts.factory;

import com.project.tshirts.builder.TShirt;
import com.project.tshirts.dto.TShirtCustomizationDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class SportTShirtFactory implements TShirtFactory {

    @Override
    public TShirt createTShirt(TShirtCustomizationDto customization) {
        // Handle null customization with default values
        if (customization == null) {
            String uniqueSku = "SPORT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            return new TShirt.TShirtBuilder()
                    .size("M")
                    .color("Black")
                    .print("Sport: Default")
                    .fabric("polyester")
                    .price(new BigDecimal("30.00"))
                    .sku(uniqueSku)
                    .build();
        }
        
        BigDecimal finalPrice = calculatePrice(customization);
        String baseSku = customization.getSku() != null ? customization.getSku() : "DEFAULT";
        String uniqueSku = "SPORT-" + baseSku + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        return new TShirt.TShirtBuilder()
                .size(customization.getSize())
                .color(customization.getColor())
                .print("Sport: " + customization.getPrint())
                .fabric("polyester") // Sport shirts always use polyester
                .price(finalPrice)
                .sku(uniqueSku)
                .build();
    }

    @Override
    public String getCategory() {
        return "sport";
    }

    private BigDecimal calculatePrice(TShirtCustomizationDto customization) {
        if (customization == null || customization.getPrice() == null) {
            return new BigDecimal("30.00");
        }
        
        BigDecimal basePrice = customization.getPrice();
        
        // Sport shirts have a premium for moisture-wicking properties
        return basePrice.multiply(BigDecimal.valueOf(1.2));
    }
}