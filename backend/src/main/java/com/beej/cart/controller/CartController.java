package com.beej.cart.controller;

import com.beej.cart.dto.*;
import com.beej.cart.service.CartService;
import com.beej.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "APIs for shopping cart management")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartController {

    private final CartService cartService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get cart by user ID", description = "Retrieve the shopping cart for a specific user")
    public ResponseEntity<ApiResponse<CartDTO>> getCartByUserId(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        CartDTO cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("Cart retrieved successfully", cart));
    }

    @GetMapping("/session/{sessionId}")
    @Operation(summary = "Get cart by session ID", description = "Retrieve the shopping cart for a guest session")
    public ResponseEntity<ApiResponse<CartDTO>> getCartBySessionId(
            @Parameter(description = "Session ID") @PathVariable String sessionId) {
        CartDTO cart = cartService.getCartBySessionId(sessionId);
        return ResponseEntity.ok(ApiResponse.success("Cart retrieved successfully", cart));
    }

    @GetMapping
    @Operation(summary = "Get cart", description = "Retrieve the shopping cart by user ID or session ID")
    public ResponseEntity<ApiResponse<CartDTO>> getCart(
            @Parameter(description = "User ID") @RequestParam(required = false) Long userId,
            @Parameter(description = "Session ID") @RequestParam(required = false) String sessionId) {
        CartDTO cart = cartService.getCart(userId, sessionId);
        return ResponseEntity.ok(ApiResponse.success("Cart retrieved successfully", cart));
    }

    @PostMapping("/add")
    @Operation(summary = "Add item to cart", description = "Add a product to the shopping cart")
    public ResponseEntity<ApiResponse<CartDTO>> addToCart(@Valid @RequestBody AddToCartRequestDTO request) {
        CartDTO cart = cartService.addToCart(request);
        return ResponseEntity.ok(ApiResponse.success("Item added to cart successfully", cart));
    }

    @PutMapping("/{cartId}/items/{itemId}")
    @Operation(summary = "Update cart item", description = "Update the quantity of a cart item")
    public ResponseEntity<ApiResponse<CartDTO>> updateCartItem(
            @Parameter(description = "Cart ID") @PathVariable Long cartId,
            @Parameter(description = "Cart Item ID") @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequestDTO request) {
        CartDTO cart = cartService.updateCartItem(cartId, itemId, request);
        return ResponseEntity.ok(ApiResponse.success("Cart item updated successfully", cart));
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    @Operation(summary = "Remove cart item", description = "Remove an item from the shopping cart")
    public ResponseEntity<ApiResponse<CartDTO>> removeCartItem(
            @Parameter(description = "Cart ID") @PathVariable Long cartId,
            @Parameter(description = "Cart Item ID") @PathVariable Long itemId) {
        CartDTO cart = cartService.removeCartItem(cartId, itemId);
        return ResponseEntity.ok(ApiResponse.success("Cart item removed successfully", cart));
    }

    @DeleteMapping("/{cartId}/clear")
    @Operation(summary = "Clear cart", description = "Remove all items from the shopping cart")
    public ResponseEntity<ApiResponse<CartDTO>> clearCart(
            @Parameter(description = "Cart ID") @PathVariable Long cartId) {
        CartDTO cart = cartService.clearCart(cartId);
        return ResponseEntity.ok(ApiResponse.success("Cart cleared successfully", cart));
    }

    @PostMapping("/{cartId}/coupon")
    @Operation(summary = "Apply coupon", description = "Apply a coupon code to the shopping cart")
    public ResponseEntity<ApiResponse<CartDTO>> applyCoupon(
            @Parameter(description = "Cart ID") @PathVariable Long cartId,
            @Valid @RequestBody ApplyCouponRequestDTO request) {
        CartDTO cart = cartService.applyCoupon(cartId, request);
        return ResponseEntity.ok(ApiResponse.success("Coupon applied successfully", cart));
    }

    @DeleteMapping("/{cartId}/coupon")
    @Operation(summary = "Remove coupon", description = "Remove the applied coupon from the shopping cart")
    public ResponseEntity<ApiResponse<CartDTO>> removeCoupon(
            @Parameter(description = "Cart ID") @PathVariable Long cartId) {
        CartDTO cart = cartService.removeCoupon(cartId);
        return ResponseEntity.ok(ApiResponse.success("Coupon removed successfully", cart));
    }

    @PostMapping("/merge")
    @Operation(summary = "Merge guest cart", description = "Merge a guest cart with a user cart after login")
    public ResponseEntity<ApiResponse<CartDTO>> mergeGuestCart(
            @Parameter(description = "Session ID") @RequestParam String sessionId,
            @Parameter(description = "User ID") @RequestParam Long userId) {
        CartDTO cart = cartService.mergeGuestCart(sessionId, userId);
        return ResponseEntity.ok(ApiResponse.success("Cart merged successfully", cart));
    }

    @DeleteMapping("/{cartId}")
    @Operation(summary = "Delete cart", description = "Delete a shopping cart")
    public ResponseEntity<ApiResponse<Void>> deleteCart(
            @Parameter(description = "Cart ID") @PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok(ApiResponse.success("Cart deleted successfully"));
    }
}
