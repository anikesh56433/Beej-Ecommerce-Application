package com.beej.cart.service;

import com.beej.cart.dto.*;
import com.beej.cart.entity.Cart;
import com.beej.cart.entity.CartItem;
import com.beej.cart.mapper.CartMapper;
import com.beej.cart.mapper.CartItemMapper;
import com.beej.cart.repository.CartItemRepository;
import com.beej.cart.repository.CartRepository;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartDTO getCartByUserId(Long userId) {
        log.info("Fetching cart for user ID: {}", userId);
        return cartRepository.findByUserId(userId)
                .map(cartMapper::toCartDTO)
                .orElseGet(() -> cartMapper.toCartDTO(createNewCart(userId, null)));
    }

    @Override
    public CartDTO getCartBySessionId(String sessionId) {
        log.info("Fetching cart for session ID: {}", sessionId);
        return cartRepository.findBySessionId(sessionId)
                .map(cartMapper::toCartDTO)
                .orElseGet(() -> cartMapper.toCartDTO(createNewCart(null, sessionId)));
    }

    @Override
    public CartDTO getCart(Long userId, String sessionId) {
        log.info("Fetching cart for user ID: {} or session ID: {}", userId, sessionId);
        Optional<Cart> cart = cartRepository.findByUserIdOrSessionId(userId, sessionId);
        
        if (cart.isPresent()) {
            return cartMapper.toCartDTO(cart.get());
        }
        
        return cartMapper.toCartDTO(createNewCart(userId, sessionId));
    }

    @Override
    @Transactional
    public CartDTO addToCart(AddToCartRequestDTO request) {
        log.info("Adding product {} to cart", request.getProductId());
        
        Cart cart = getOrCreateCart(request.getUserId(), request.getSessionId());
        
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), request.getProductId());
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            item.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            cartItemRepository.save(item);
            log.info("Updated existing cart item quantity to {}", item.getQuantity());
        } else {
            CartItem newItem = createCartItem(cart, request.getProductId(), request.getQuantity());
            cartItemRepository.save(newItem);
            log.info("Added new cart item");
        }
        
        return recalculateCart(cart);
    }

    @Override
    @Transactional
    public CartDTO updateCartItem(Long cartId, Long itemId, UpdateCartItemRequestDTO request) {
        log.info("Updating cart item {} in cart {}", itemId, cartId);
        
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
        
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));
        
        if (request.getQuantity() == 0) {
            cartItemRepository.delete(item);
            log.info("Removed cart item due to zero quantity");
        } else {
            item.setQuantity(request.getQuantity());
            item.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(item);
            log.info("Updated cart item quantity to {}", request.getQuantity());
        }
        
        return recalculateCart(cart);
    }

    @Override
    @Transactional
    public CartDTO removeCartItem(Long cartId, Long itemId) {
        log.info("Removing cart item {} from cart {}", itemId, cartId);
        
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
        
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));
        
        cartItemRepository.delete(item);
        log.info("Removed cart item");
        
        return recalculateCart(cart);
    }

    @Override
    @Transactional
    public CartDTO clearCart(Long cartId) {
        log.info("Clearing cart {}", cartId);
        
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
        
        cartItemRepository.deleteByCartId(cartId);
        
        cart.setSubtotal(BigDecimal.ZERO);
        cart.setTaxAmount(BigDecimal.ZERO);
        cart.setShippingAmount(BigDecimal.ZERO);
        cart.setDiscountAmount(BigDecimal.ZERO);
        cart.setTotal(BigDecimal.ZERO);
        cart.setCouponCode(null);
        cart.setCouponDiscount(BigDecimal.ZERO);
        cart.setItemCount(0);
        
        Cart saved = cartRepository.save(cart);
        log.info("Cleared cart");
        
        return cartMapper.toCartDTO(saved);
    }

    @Override
    @Transactional
    public CartDTO applyCoupon(Long cartId, ApplyCouponRequestDTO request) {
        log.info("Applying coupon {} to cart {}", request.getCouponCode(), cartId);
        
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
        
        cart.setCouponCode(request.getCouponCode());
        
        BigDecimal discount = calculateCouponDiscount(request.getCouponCode(), cart.getSubtotal());
        cart.setCouponDiscount(discount);
        
        CartDTO savedDTO = recalculateCart(cart);
        log.info("Applied coupon with discount: {}", discount);
        
        return savedDTO;
    }

    @Override
    @Transactional
    public CartDTO removeCoupon(Long cartId) {
        log.info("Removing coupon from cart {}", cartId);
        
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
        
        cart.setCouponCode(null);
        cart.setCouponDiscount(BigDecimal.ZERO);
        
        CartDTO savedDTO = recalculateCart(cart);
        log.info("Removed coupon");
        
        return savedDTO;
    }

    @Override
    @Transactional
    public CartDTO mergeGuestCart(String sessionId, Long userId) {
        log.info("Merging guest cart {} for user {}", sessionId, userId);
        
        Optional<Cart> guestCart = cartRepository.findBySessionId(sessionId);
        Optional<Cart> userCart = cartRepository.findByUserId(userId);
        
        if (guestCart.isEmpty()) {
            log.info("No guest cart found, returning user cart");
            return getCartByUserId(userId);
        }
        
        Cart guest = guestCart.get();
        
        if (userCart.isPresent()) {
            Cart user = userCart.get();
            
            List<CartItem> guestItems = cartItemRepository.findByCartId(guest.getId());
            for (CartItem guestItem : guestItems) {
                Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(
                    user.getId(), guestItem.getProductId());
                
                if (existingItem.isPresent()) {
                    CartItem item = existingItem.get();
                    item.setQuantity(item.getQuantity() + guestItem.getQuantity());
                    item.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    cartItemRepository.save(item);
                } else {
                    guestItem.setCart(user);
                    cartItemRepository.save(guestItem);
                }
            }
            
            cartRepository.delete(guest);
            return recalculateCart(user);
        } else {
            guest.setUserId(userId);
            guest.setSessionId(null);
            Cart saved = cartRepository.save(guest);
            return cartMapper.toCartDTO(saved);
        }
    }

    @Override
    @Transactional
    public void deleteCart(Long cartId) {
        log.info("Deleting cart {}", cartId);
        
        if (!cartRepository.existsById(cartId)) {
            throw new ResourceNotFoundException("Cart not found with id: " + cartId);
        }
        
        cartItemRepository.deleteByCartId(cartId);
        cartRepository.deleteById(cartId);
        log.info("Deleted cart");
    }

    private Cart getOrCreateCart(Long userId, String sessionId) {
        Optional<Cart> cart = cartRepository.findByUserIdOrSessionId(userId, sessionId);
        
        if (cart.isPresent()) {
            return cart.get();
        }
        
        return createNewCart(userId, sessionId);
    }

    private Cart createNewCart(Long userId, String sessionId) {
        Cart cart = Cart.builder()
                .userId(userId)
                .sessionId(sessionId)
                .subtotal(BigDecimal.ZERO)
                .taxAmount(BigDecimal.ZERO)
                .shippingAmount(BigDecimal.ZERO)
                .discountAmount(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .currency("USD")
                .itemCount(0)
                .build();
        
        Cart saved = cartRepository.save(cart);
        log.info("Created new cart with ID: {}", saved.getId());
        
        return saved;
    }

    private CartItem createCartItem(Cart cart, Long productId, Integer quantity) {
        return CartItem.builder()
                .cart(cart)
                .productId(productId)
                .productName("Product " + productId)
                .productSku("SKU-" + productId)
                .quantity(quantity)
                .unitPrice(BigDecimal.ZERO)
                .totalPrice(BigDecimal.ZERO)
                .inStock(true)
                .build();
    }

    private CartDTO recalculateCart(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        cart.setItems(items);
        
        BigDecimal subtotal = items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal discount = cart.getDiscountAmount().add(cart.getCouponDiscount());
        BigDecimal subtotalAfterDiscount = subtotal.subtract(discount);
        
        BigDecimal taxAmount = subtotalAfterDiscount.multiply(BigDecimal.valueOf(0.1));
        BigDecimal shippingAmount = subtotalAfterDiscount.compareTo(BigDecimal.valueOf(50)) >= 0 
                ? BigDecimal.ZERO 
                : BigDecimal.valueOf(9.99);
        
        BigDecimal total = subtotalAfterDiscount.add(taxAmount).add(shippingAmount);
        
        cart.setSubtotal(subtotal);
        cart.setTaxAmount(taxAmount);
        cart.setShippingAmount(shippingAmount);
        cart.setDiscountAmount(discount);
        cart.setTotal(total);
        cart.setItemCount(items.size());
        
        Cart saved = cartRepository.save(cart);
        return cartMapper.toCartDTO(saved);
    }

    private BigDecimal calculateCouponDiscount(String couponCode, BigDecimal subtotal) {
        return BigDecimal.ZERO;
    }
}
