package com.beej.wishlist.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
public class UpdateWishlistItemRequestDTO {
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    @Size(max = 20, message = "Priority must not exceed 20 characters")
    private String priority;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    
    @DecimalMin(value = "0.00", message = "Price must be positive")
    private BigDecimal unitPrice;
}
