package com.beej.wishlist.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToWishlistRequestDTO {
    
    @NotNull(message = "Wishlist ID is required")
    private Long wishlistId;
    
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    private String productName;
    
    @Size(max = 100, message = "Product SKU must not exceed 100 characters")
    private String productSku;
    
    @Size(max = 500, message = "Product image must not exceed 500 characters")
    private String productImage;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity = 1;
    
    @DecimalMin(value = "0.00", message = "Price must be positive")
    private BigDecimal unitPrice;
    
    @Size(max = 20, message = "Priority must not exceed 20 characters")
    private String priority = "NORMAL";
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
}
