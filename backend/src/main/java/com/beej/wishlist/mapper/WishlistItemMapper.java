package com.beej.wishlist.mapper;

import com.beej.wishlist.dto.WishlistItemDTO;
import com.beej.wishlist.entity.WishlistItem;
import org.springframework.stereotype.Component;

@Component
public class WishlistItemMapper {

    public WishlistItemDTO toWishlistItemDTO(WishlistItem item) {
        if (item == null) {
            return null;
        }

        return WishlistItemDTO.builder()
                .id(item.getId())
                .wishlistId(item.getWishlist() != null ? item.getWishlist().getId() : null)
                .productId(item.getProductId())
                .productName(item.getProductName())
                .productSku(item.getProductSku())
                .productImage(item.getProductImage())
                .productUrl(item.getProductUrl())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .addedPrice(item.getAddedPrice())
                .priceChange(item.getPriceChange())
                .isPriceDropped(item.getIsPriceDropped())
                .isOnSale(item.getIsOnSale())
                .salePrice(item.getSalePrice())
                .priority(item.getPriority())
                .notes(item.getNotes())
                .isAvailable(item.getIsAvailable())
                .isNotified(item.getIsNotified())
                .notifiedAt(item.getNotifiedAt())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    public WishlistItem toEntity(WishlistItemDTO dto) {
        if (dto == null) {
            return null;
        }

        WishlistItem item = WishlistItem.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .productSku(dto.getProductSku())
                .productImage(dto.getProductImage())
                .productUrl(dto.getProductUrl())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .totalPrice(dto.getTotalPrice())
                .addedPrice(dto.getAddedPrice())
                .priceChange(dto.getPriceChange())
                .isPriceDropped(dto.getIsPriceDropped())
                .isOnSale(dto.getIsOnSale())
                .salePrice(dto.getSalePrice())
                .priority(dto.getPriority())
                .notes(dto.getNotes())
                .isAvailable(dto.getIsAvailable())
                .isNotified(dto.getIsNotified())
                .notifiedAt(dto.getNotifiedAt())
                .build();

        return item;
    }
}
