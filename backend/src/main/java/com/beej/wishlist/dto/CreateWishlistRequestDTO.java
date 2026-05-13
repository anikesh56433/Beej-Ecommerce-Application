package com.beej.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWishlistRequestDTO {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Wishlist name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    private Boolean isDefault = false;
    
    private Boolean isPrivate = true;
    
    private Boolean notifyOnPriceDrop = true;
    
    private Boolean notifyOnBackInStock = true;
}
