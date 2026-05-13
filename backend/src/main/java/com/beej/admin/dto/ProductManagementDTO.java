package com.beej.admin.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ProductManagementDTO {
    
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    private String name;
    
    private String description;
    
    @Size(max = 500, message = "Short description must not exceed 500 characters")
    private String shortDescription;
    
    @NotBlank(message = "SKU is required")
    @Size(max = 100, message = "SKU must not exceed 100 characters")
    private String sku;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
    
    private BigDecimal comparePrice;
    private BigDecimal costPrice;
    private BigDecimal weight;
    private String dimensions;
    
    @NotNull(message = "Category is required")
    private Long categoryId;
    private String categoryName;
    
    private String brand;
    private String tags;
    
    @NotBlank(message = "Status is required")
    private String status;
    
    private Boolean trackInventory;
    private Boolean featured;
    
    private List<String> imageUrls;
    private Integer stockQuantity;
    private Integer availableQuantity;
    private Integer soldQuantity;
    private Double averageRating;
    private Integer reviewCount;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCreateRequest {
        @NotBlank
        @Size(max = 255)
        private String name;
        
        private String description;
        
        @Size(max = 500)
        private String shortDescription;
        
        @NotBlank
        @Size(max = 100)
        private String sku;
        
        @NotNull
        @DecimalMin(value = "0.01")
        private BigDecimal price;
        
        private BigDecimal comparePrice;
        private BigDecimal costPrice;
        private BigDecimal weight;
        private String dimensions;
        
        @NotNull
        private Long categoryId;
        
        private String brand;
        private String tags;
        
        @NotBlank
        private String status;
        
        private Boolean trackInventory = true;
        private Boolean featured = false;
        
        private List<String> imageUrls;
        private Integer stockQuantity;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductUpdateRequest {
        @Size(max = 255)
        private String name;
        
        private String description;
        
        @Size(max = 500)
        private String shortDescription;
        
        @Size(max = 100)
        private String sku;
        
        @DecimalMin(value = "0.01")
        private BigDecimal price;
        
        private BigDecimal comparePrice;
        private BigDecimal costPrice;
        private BigDecimal weight;
        private String dimensions;
        
        private Long categoryId;
        private String brand;
        private String tags;
        
        private String status;
        private Boolean trackInventory;
        private Boolean featured;
        
        private List<String> imageUrls;
        private Integer stockQuantity;
    }
}
