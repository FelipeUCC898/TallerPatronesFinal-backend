package com.project.tshirts.repository;

import com.project.tshirts.model.TShirtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TShirtRepository extends JpaRepository<TShirtEntity, Long> {
    
    Optional<TShirtEntity> findBySku(String sku);
    
    List<TShirtEntity> findByCategory(String category);
    
    List<TShirtEntity> findBySize(String size);
    
    List<TShirtEntity> findByColor(String color);
    
    boolean existsBySku(String sku);
}