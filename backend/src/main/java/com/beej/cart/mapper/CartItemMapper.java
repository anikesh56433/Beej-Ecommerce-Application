package com.beej.cart.mapper;

import com.beej.cart.dto.CartItemDTO;
import com.beej.cart.entity.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemDTO toCartItemDTO(CartItem item) {
        if (item == null) {
            return null;
        }

        return CartItemDTO.builder()
                .id(item.getId())
                .cartId(item.getCart() != null ? item.getCart().getId() : null)
                .productId(item.getProductId())
                .productName(item.getProductName())
                .productSku(item.getProductSku())
                .productImage(item.getProductImage())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .comparePrice(item.getComparePrice())
                .discountAmount(item.getDiscountAmount())
                .inStock(item.getInStock())
                .availableQuantity(item.getAvailableQuantity())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    public CartItem toEntity(CartItemDTO dto) {
        if (dto == null) {
            return null;
        }

        CartItem item = CartItem.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .productSku(dto.getProductSku())
                .productImage(dto.getProductImage())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .totalPrice(dto.getTotalPrice())
                .comparePrice(dto.getComparePrice())
                .discountAmount(dto.getDiscountAmount())
                .inStock(dto.getInStock())
                .availableQuantity(dto.getAvailableQuantity())
                .build();

        return item;
    }
}
