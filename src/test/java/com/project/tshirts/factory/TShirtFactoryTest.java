package com.project.tshirts.factory;

import com.project.tshirts.dto.TShirtCustomizationDto;
import com.project.tshirts.builder.TShirt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TShirtFactoryTest {

    @Test
    @DisplayName("Should create casual T-Shirt with correct properties")
    public void testCasualTShirtFactory() {
        TShirtFactory factory = new CasualTShirtFactory();
        TShirtCustomizationDto dto = new TShirtCustomizationDto();
        dto.setSize("M");
        dto.setColor("Azul");
        dto.setPrint("Logo Java");
        dto.setFabric("cotton");
        dto.setPrice(new java.math.BigDecimal("25.00"));
        dto.setSku("TEST-001");
        dto.setCategory("casual");

        TShirt tshirt = factory.createTShirt(dto);

        assertNotNull(tshirt);
        assertEquals("M", tshirt.getSize());
        assertEquals("Azul", tshirt.getColor());
        assertEquals("Casual: Logo Java", tshirt.getPrint());
        assertEquals("cotton", tshirt.getFabric());
        assertTrue(tshirt.getPrice().compareTo(new java.math.BigDecimal("0")) > 0);
        assertTrue(tshirt.getSku().startsWith("CAS-"));
    }

    @Test
    @DisplayName("Should create sport T-Shirt with correct properties")
    public void testSportTShirtFactory() {
        TShirtFactory factory = new SportTShirtFactory();
        TShirtCustomizationDto dto = new TShirtCustomizationDto();
        dto.setSize("L");
        dto.setColor("Negro");
        dto.setPrint("Estrella");
        dto.setFabric("polyester");
        dto.setPrice(new java.math.BigDecimal("30.00"));
        dto.setSku("TEST-002");
        dto.setCategory("sport");

        TShirt tshirt = factory.createTShirt(dto);

        assertNotNull(tshirt);
        assertEquals("L", tshirt.getSize());
        assertEquals("Negro", tshirt.getColor());
        assertEquals("Sport: Estrella", tshirt.getPrint());
        assertEquals("polyester", tshirt.getFabric());
        assertTrue(tshirt.getPrice().compareTo(new java.math.BigDecimal("0")) > 0);
        assertTrue(tshirt.getSku().startsWith("SPORT-"));
    }



    @Test
    @DisplayName("Should create premium T-Shirt with correct properties")
    public void testPremiumTShirtFactory() {
        TShirtFactory factory = new PremiumTShirtFactory();
        TShirtCustomizationDto dto = new TShirtCustomizationDto();
        dto.setSize("S");
        dto.setColor("Blanco");
        dto.setPrint("Diseño Premium");
        dto.setFabric("egyptian cotton");
        dto.setPrice(new java.math.BigDecimal("50.00"));
        dto.setSku("TEST-004");
        dto.setCategory("premium");

        TShirt tshirt = factory.createTShirt(dto);

        assertNotNull(tshirt);
        assertEquals("S", tshirt.getSize());
        assertEquals("Blanco", tshirt.getColor());
        assertEquals("Premium: Diseño Premium", tshirt.getPrint());
        assertEquals("cotton", tshirt.getFabric()); // Premium shirts always use cotton
        assertTrue(tshirt.getPrice().compareTo(new java.math.BigDecimal("0")) > 0);
        assertTrue(tshirt.getSku().startsWith("PREMIUM-"));
    }

    @Test
    @DisplayName("Should apply different pricing strategies for each category")
    public void testPricingStrategies() {
        TShirtCustomizationDto dto = new TShirtCustomizationDto();
        dto.setSize("M");
        dto.setColor("Rojo");
        dto.setPrint("Logo Spring");
        dto.setFabric("cotton");
        dto.setPrice(new java.math.BigDecimal("25.00"));
        dto.setSku("TEST-PRICE");
        dto.setCategory("test");

        TShirt casual = new CasualTShirtFactory().createTShirt(dto);
        TShirt sport = new SportTShirtFactory().createTShirt(dto);
        TShirt premium = new PremiumTShirtFactory().createTShirt(dto);

        assertTrue(sport.getPrice().compareTo(casual.getPrice()) > 0);
        assertTrue(premium.getPrice().compareTo(sport.getPrice()) > 0);
    }

    @Test
    @DisplayName("Should return correct category for each factory")
    public void testFactoryCategories() {
        assertEquals("casual", new CasualTShirtFactory().getCategory());
        assertEquals("sport", new SportTShirtFactory().getCategory());
        assertEquals("premium", new PremiumTShirtFactory().getCategory());
    }

    @Test
    @DisplayName("Should handle null DTO gracefully")
    public void testNullDtoHandling() {
        TShirtFactory factory = new CasualTShirtFactory();
        
        TShirt result = factory.createTShirt(null);
        
        // Should handle null gracefully, not throw exception
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should generate unique SKUs for same customization")
    public void testUniqueSkuGeneration() {
        TShirtFactory factory = new CasualTShirtFactory();
        TShirtCustomizationDto dto1 = new TShirtCustomizationDto();
        dto1.setSize("L");
        dto1.setColor("Verde");
        dto1.setPrint("Logo");
        dto1.setFabric("cotton");
        dto1.setPrice(new java.math.BigDecimal("25.00"));
        dto1.setSku("TEST-SAME");
        dto1.setCategory("casual");
        
        TShirtCustomizationDto dto2 = new TShirtCustomizationDto();
        dto2.setSize("L");
        dto2.setColor("Verde");
        dto2.setPrint("Logo");
        dto2.setFabric("cotton");
        dto2.setPrice(new java.math.BigDecimal("25.00"));
        dto2.setSku("TEST-SAME");
        dto2.setCategory("casual");

        TShirt tshirt1 = factory.createTShirt(dto1);
        TShirt tshirt2 = factory.createTShirt(dto2);

        assertNotEquals(tshirt1.getSku(), tshirt2.getSku());
    }
}