package com.beej.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemDTO {
    
    private Long id;
    private Long wishlistId;
    private Long productId;
    private String productName;
    private String productSku;
    private String productImage;
    private String productUrl;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal addedPrice;
    private BigDecimal priceChange;
    private Boolean isPriceDropped;
    private Boolean isOnSale;
    private BigDecimal salePrice;
    private String priority;
    private String notes;
    private Boolean isAvailable;
    private Boolean isNotified;
    private LocalDateTime notifiedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
