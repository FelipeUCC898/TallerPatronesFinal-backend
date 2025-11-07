package com.project.tshirts.service;

import com.project.tshirts.dto.TShirtCustomizationDto;
import com.project.tshirts.dto.TShirtResponseDto;

import java.util.List;

public interface TShirtService {
    TShirtResponseDto createTShirt(TShirtCustomizationDto customization);
    TShirtResponseDto getTShirtById(Long id);
    List<TShirtResponseDto> getAllTShirts();
    List<TShirtResponseDto> getTShirtsByCategory(String category);
    List<TShirtResponseDto> getTShirtsBySize(String size);
    List<TShirtResponseDto> getTShirtsByColor(String color);
}