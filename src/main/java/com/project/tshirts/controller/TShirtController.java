package com.project.tshirts.controller;

import com.project.tshirts.dto.TShirtCustomizationDto;
import com.project.tshirts.dto.TShirtResponseDto;
import com.project.tshirts.service.TShirtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tshirts")
@Tag(name = "T-Shirts", description = "API para gestionar camisetas personalizadas")
@Slf4j
public class TShirtController {

    private final TShirtService tshirtService;

    @Autowired
    public TShirtController(TShirtService tshirtService) {
        this.tshirtService = tshirtService;
    }

    @PostMapping
    @Operation(summary = "Crear una camiseta personalizada", description = "Crea una nueva camiseta usando patrones Builder y Abstract Factory")
    public ResponseEntity<TShirtResponseDto> createTShirt(@Valid @RequestBody TShirtCustomizationDto customization) {
        log.info("Creating TShirt with category: {}", customization.getCategory());
        TShirtResponseDto response = tshirtService.createTShirt(customization);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener camiseta por ID", description = "Obtiene los detalles de una camiseta específica")
    public ResponseEntity<TShirtResponseDto> getTShirtById(@PathVariable Long id) {
        log.info("Getting TShirt by ID: {}", id);
        TShirtResponseDto response = tshirtService.getTShirtById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las camisetas", description = "Obtiene una lista de todas las camisetas")
    public ResponseEntity<List<TShirtResponseDto>> getAllTShirts() {
        log.info("Getting all TShirts");
        List<TShirtResponseDto> response = tshirtService.getAllTShirts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Obtener camisetas por categoría", description = "Obtiene camisetas filtradas por categoría (casual, sport, premium)")
    public ResponseEntity<List<TShirtResponseDto>> getTShirtsByCategory(@PathVariable String category) {
        log.info("Getting TShirts by category: {}", category);
        List<TShirtResponseDto> response = tshirtService.getTShirtsByCategory(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{size}")
    @Operation(summary = "Obtener camisetas por talla", description = "Obtiene camisetas filtradas por talla")
    public ResponseEntity<List<TShirtResponseDto>> getTShirtsBySize(@PathVariable String size) {
        log.info("Getting TShirts by size: {}", size);
        List<TShirtResponseDto> response = tshirtService.getTShirtsBySize(size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/color/{color}")
    @Operation(summary = "Obtener camisetas por color", description = "Obtiene camisetas filtradas por color")
    public ResponseEntity<List<TShirtResponseDto>> getTShirtsByColor(@PathVariable String color) {
        log.info("Getting TShirts by color: {}", color);
        List<TShirtResponseDto> response = tshirtService.getTShirtsByColor(color);
        return ResponseEntity.ok(response);
    }
}