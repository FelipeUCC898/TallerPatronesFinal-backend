package com.project.tshirts.service.impl;

import com.project.tshirts.builder.TShirt;
import com.project.tshirts.dto.TShirtCustomizationDto;
import com.project.tshirts.dto.TShirtResponseDto;
import com.project.tshirts.factory.TShirtFactory;
import com.project.tshirts.model.TShirtEntity;
import com.project.tshirts.repository.TShirtRepository;
import com.project.tshirts.service.TShirtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TShirtServiceImpl implements TShirtService {

    private final TShirtRepository tshirtRepository;
    private final Map<String, TShirtFactory> factoryMap;

    @Autowired
    public TShirtServiceImpl(TShirtRepository tshirtRepository, List<TShirtFactory> factories) {
        this.tshirtRepository = tshirtRepository;
        this.factoryMap = factories.stream()
                .collect(Collectors.toMap(TShirtFactory::getCategory, factory -> factory));
        log.info("TShirtService initialized with factories: {}", factoryMap.keySet());
    }

    @Override
    public TShirtResponseDto createTShirt(TShirtCustomizationDto customization) {
        log.info("Creating TShirt with category: {}", customization.getCategory());
        
        TShirtFactory factory = factoryMap.get(customization.getCategory().toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Categoría no válida: " + customization.getCategory());
        }
        
        // Use Builder pattern to create TShirt
        TShirt tshirt = factory.createTShirt(customization);
        
        // Convert to entity and save
        TShirtEntity entity = convertToEntity(tshirt, factory.getCategory());
        TShirtEntity savedEntity = tshirtRepository.save(entity);
        
        log.info("TShirt created successfully with ID: {}", savedEntity.getId());
        return convertToDto(savedEntity);
    }

    @Override
    public TShirtResponseDto getTShirtById(Long id) {
        TShirtEntity entity = tshirtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TShirt no encontrado con ID: " + id));
        return convertToDto(entity);
    }

    @Override
    public List<TShirtResponseDto> getAllTShirts() {
        return tshirtRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TShirtResponseDto> getTShirtsByCategory(String category) {
        return tshirtRepository.findByCategory(category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TShirtResponseDto> getTShirtsBySize(String size) {
        return tshirtRepository.findBySize(size).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TShirtResponseDto> getTShirtsByColor(String color) {
        return tshirtRepository.findByColor(color).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TShirtEntity convertToEntity(TShirt tshirt, String category) {
        TShirtEntity entity = new TShirtEntity();
        entity.setSize(tshirt.getSize());
        entity.setColor(tshirt.getColor());
        entity.setPrint(tshirt.getPrint());
        entity.setFabric(tshirt.getFabric());
        entity.setPrice(tshirt.getPrice());
        entity.setSku(tshirt.getSku());
        entity.setCategory(category);
        return entity;
    }

    private TShirtResponseDto convertToDto(TShirtEntity entity) {
        TShirtResponseDto dto = new TShirtResponseDto();
        dto.setId(entity.getId());
        dto.setSize(entity.getSize());
        dto.setColor(entity.getColor());
        dto.setPrint(entity.getPrint());
        dto.setFabric(entity.getFabric());
        dto.setPrice(entity.getPrice());
        dto.setSku(entity.getSku());
        dto.setCategory(entity.getCategory());
        return dto;
    }
}