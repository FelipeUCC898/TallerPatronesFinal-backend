package com.project.tshirts.factory;

import com.project.tshirts.builder.TShirt;
import com.project.tshirts.dto.TShirtCustomizationDto;

public interface TShirtFactory {
    TShirt createTShirt(TShirtCustomizationDto customization);
    String getCategory();
}