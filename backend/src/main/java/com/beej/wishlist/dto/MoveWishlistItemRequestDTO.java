package com.beej.wishlist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveWishlistItemRequestDTO {
    
    @NotNull(message = "Item ID is required")
    private Long itemId;
    
    @NotNull(message = "Source wishlist ID is required")
    private Long sourceWishlistId;
    
    @NotNull(message = "Target wishlist ID is required")
    private Long targetWishlistId;
    
    private Long userId;
}
