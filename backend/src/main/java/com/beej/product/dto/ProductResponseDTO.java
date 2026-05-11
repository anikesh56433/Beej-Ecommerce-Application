package com.beej.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    private String shortDescription;
    private String sku;
    private BigDecimal price;
    private BigDecimal comparePrice;
    private String status;
    private Boolean featured;
    private CategoryDTO category;
    private String brand;
    private List<String> images;
    private Integer stockQuantity;
    private Integer availableQuantity;
    private Double averageRating;
    private Integer reviewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDTO {
        private Long id;
        private String name;
        private String slug;
        private String imageUrl;
    }
}
