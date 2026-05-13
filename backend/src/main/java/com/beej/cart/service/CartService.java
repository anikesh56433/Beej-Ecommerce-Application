package com.beej.cart.service;

import com.beej.cart.dto.*;

public interface CartService {
    
    CartDTO getCartByUserId(Long userId);
    
    CartDTO getCartBySessionId(String sessionId);
    
    CartDTO getCart(Long userId, String sessionId);
    
    CartDTO addToCart(AddToCartRequestDTO request);
    
    CartDTO updateCartItem(Long cartId, Long itemId, UpdateCartItemRequestDTO request);
    
    CartDTO removeCartItem(Long cartId, Long itemId);
    
    CartDTO clearCart(Long cartId);
    
    CartDTO applyCoupon(Long cartId, ApplyCouponRequestDTO request);
    
    CartDTO removeCoupon(Long cartId);
    
    CartDTO mergeGuestCart(String sessionId, Long userId);
    
    void deleteCart(Long cartId);
}
