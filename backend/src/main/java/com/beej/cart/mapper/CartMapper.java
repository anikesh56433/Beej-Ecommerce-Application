package com.beej.cart.mapper;

import com.beej.cart.dto.CartDTO;
import com.beej.cart.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    public CartMapper(CartItemMapper cartItemMapper) {
        this.cartItemMapper = cartItemMapper;
    }

    public CartDTO toCartDTO(Cart cart) {
        if (cart == null) {
            return null;
        }

        return CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .sessionId(cart.getSessionId())
                .subtotal(cart.getSubtotal())
                .taxAmount(cart.getTaxAmount())
                .shippingAmount(cart.getShippingAmount())
                .discountAmount(cart.getDiscountAmount())
                .total(cart.getTotal())
                .currency(cart.getCurrency())
                .couponCode(cart.getCouponCode())
                .couponDiscount(cart.getCouponDiscount())
                .items(cart.getItems() != null ? 
                    cart.getItems().stream()
                        .map(cartItemMapper::toCartItemDTO)
                        .collect(Collectors.toList()) : null)
                .itemCount(cart.getItemCount())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    public Cart toEntity(CartDTO dto) {
        if (dto == null) {
            return null;
        }

        return Cart.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .sessionId(dto.getSessionId())
                .subtotal(dto.getSubtotal())
                .taxAmount(dto.getTaxAmount())
                .shippingAmount(dto.getShippingAmount())
                .discountAmount(dto.getDiscountAmount())
                .total(dto.getTotal())
                .currency(dto.getCurrency())
                .couponCode(dto.getCouponCode())
                .couponDiscount(dto.getCouponDiscount())
                .items(dto.getItems() != null ? 
                    dto.getItems().stream()
                        .map(cartItemMapper::toEntity)
                        .collect(Collectors.toList()) : null)
                .itemCount(dto.getItemCount())
                .build();
    }
}
