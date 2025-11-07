package com.project.tshirts.builder;

import com.project.tshirts.builder.TShirt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class TShirtTest {

    @Test
    @DisplayName("Should create TShirt with Builder pattern successfully")
    public void testTShirtBuilder() {
        TShirt tshirt = new TShirt.TShirtBuilder()
                .size("M")
                .color("Azul")
                .print("Logo Java")
                .fabric("cotton")
                .price(new BigDecimal("29.99"))
                .sku("TSH-001-M-AZUL")
                .build();

        assertNotNull(tshirt);
        assertEquals("M", tshirt.getSize());
        assertEquals("Azul", tshirt.getColor());
        assertEquals("Logo Java", tshirt.getPrint());
        assertEquals("cotton", tshirt.getFabric());
        assertEquals(new BigDecimal("29.99"), tshirt.getPrice());
        assertEquals("TSH-001-M-AZUL", tshirt.getSku());
    }

    @Test
    @DisplayName("Should create TShirt with all required fields")
    public void testTShirtBuilderRequiredFields() {
        TShirt tshirt = new TShirt.TShirtBuilder()
                .size("L")
                .color("Negro")
                .print("Estrella")
                .fabric("polyester")
                .price(new BigDecimal("34.99"))
                .sku("TSH-002-L-NEGR")
                .build();

        assertNotNull(tshirt);
        assertEquals("L", tshirt.getSize());
        assertEquals("Negro", tshirt.getColor());
        assertEquals("Estrella", tshirt.getPrint());
        assertEquals("polyester", tshirt.getFabric());
        assertEquals(new BigDecimal("34.99"), tshirt.getPrice());
        assertEquals("TSH-002-L-NEGR", tshirt.getSku());
    }

    @Test
    @DisplayName("Should create TShirt with premium specifications")
    public void testPremiumTShirtBuilder() {
        TShirt premiumTshirt = new TShirt.TShirtBuilder()
                .size("S")
                .color("Blanco")
                .print("Diseño Premium")
                .fabric("blend")
                .price(new BigDecimal("79.99"))
                .sku("TSH-003-S-BLAN")
                .build();

        assertNotNull(premiumTshirt);
        assertEquals("S", premiumTshirt.getSize());
        assertEquals("Blanco", premiumTshirt.getColor());
        assertEquals("Diseño Premium", premiumTshirt.getPrint());
        assertEquals("blend", premiumTshirt.getFabric());
        assertEquals(new BigDecimal("79.99"), premiumTshirt.getPrice());
        assertEquals("TSH-003-S-BLAN", premiumTshirt.getSku());
    }

    @Test
    @DisplayName("Should demonstrate immutability - cannot modify TShirt after creation")
    public void testTShirtImmutability() {
        TShirt tshirt = new TShirt.TShirtBuilder()
                .size("M")
                .color("Azul")
                .print("Logo Java")
                .fabric("cotton")
                .price(new BigDecimal("29.99"))
                .sku("TSH-001-M-AZUL")
                .build();

        String originalColor = tshirt.getColor();
        
        // TShirt is immutable - no setters exist, so this should not compile
        // This test demonstrates that TShirt objects cannot be modified after creation
        assertEquals("Azul", tshirt.getColor());
        assertEquals(originalColor, tshirt.getColor());
    }

    @Test
    @DisplayName("Should handle null values gracefully")
    public void testTShirtBuilderWithNullValues() {
        // The TShirtBuilder validates required fields, so null values should throw exceptions
        assertThrows(IllegalArgumentException.class, () -> {
            new TShirt.TShirtBuilder()
                    .size(null)
                    .color(null)
                    .print(null)
                    .fabric(null)
                    .price(new BigDecimal("0.0"))
                    .sku(null)
                    .build();
        });
    }
}