package com.beej.wishlist.dto;

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
public class WishlistDTO {
    
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Boolean isDefault;
    private Boolean isPrivate;
    private String shareToken;
    private Integer itemCount;
    private BigDecimal totalValue;
    private Boolean notifyOnPriceDrop;
    private Boolean notifyOnBackInStock;
    private LocalDateTime lastViewedAt;
    private List<WishlistItemDTO> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
