package com.beej.wishlist.mapper;

import com.beej.wishlist.dto.WishlistDTO;
import com.beej.wishlist.entity.Wishlist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WishlistMapper {

    private final WishlistItemMapper wishlistItemMapper;

    public WishlistMapper(WishlistItemMapper wishlistItemMapper) {
        this.wishlistItemMapper = wishlistItemMapper;
    }

    public WishlistDTO toWishlistDTO(Wishlist wishlist) {
        if (wishlist == null) {
            return null;
        }

        return WishlistDTO.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUserId())
                .name(wishlist.getName())
                .description(wishlist.getDescription())
                .isDefault(wishlist.getIsDefault())
                .isPrivate(wishlist.getIsPrivate())
                .shareToken(wishlist.getShareToken())
                .itemCount(wishlist.getItemCount())
                .totalValue(wishlist.getTotalValue())
                .notifyOnPriceDrop(wishlist.getNotifyOnPriceDrop())
                .notifyOnBackInStock(wishlist.getNotifyOnBackInStock())
                .lastViewedAt(wishlist.getLastViewedAt())
                .items(wishlist.getItems() != null ? 
                    wishlist.getItems().stream()
                        .map(wishlistItemMapper::toWishlistItemDTO)
                        .collect(Collectors.toList()) : null)
                .createdAt(wishlist.getCreatedAt())
                .updatedAt(wishlist.getUpdatedAt())
                .build();
    }

    public Wishlist toEntity(WishlistDTO dto) {
        if (dto == null) {
            return null;
        }

        return Wishlist.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .name(dto.getName())
                .description(dto.getDescription())
                .isDefault(dto.getIsDefault())
                .isPrivate(dto.getIsPrivate())
                .shareToken(dto.getShareToken())
                .itemCount(dto.getItemCount())
                .totalValue(dto.getTotalValue())
                .notifyOnPriceDrop(dto.getNotifyOnPriceDrop())
                .notifyOnBackInStock(dto.getNotifyOnBackInStock())
                .lastViewedAt(dto.getLastViewedAt())
                .build();
    }
}
