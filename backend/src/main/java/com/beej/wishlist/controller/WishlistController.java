package com.beej.wishlist.controller;

import com.beej.wishlist.dto.*;
import com.beej.wishlist.service.WishlistService;
import com.beej.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
@Tag(name = "Wishlist Management", description = "APIs for managing wishlists")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    @Operation(summary = "Create wishlist", description = "Create a new wishlist")
    public ResponseEntity<ApiResponse<WishlistDTO>> createWishlist(@Valid @RequestBody CreateWishlistRequestDTO request) {
        WishlistDTO wishlist = wishlistService.createWishlist(request);
        return ResponseEntity.ok(ApiResponse.success("Wishlist created successfully", wishlist));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get wishlist by ID", description = "Retrieve a specific wishlist by its ID")
    public ResponseEntity<ApiResponse<WishlistDTO>> getWishlistById(
            @Parameter(description = "Wishlist ID") @PathVariable Long id) {
        WishlistDTO wishlist = wishlistService.getWishlistById(id);
        return ResponseEntity.ok(ApiResponse.success("Wishlist retrieved successfully", wishlist));
    }

    @GetMapping("/share/{shareToken}")
    @Operation(summary = "Get wishlist by share token", description = "Retrieve a wishlist by its share token")
    public ResponseEntity<ApiResponse<WishlistDTO>> getWishlistByShareToken(
            @Parameter(description = "Share token") @PathVariable String shareToken) {
        WishlistDTO wishlist = wishlistService.getWishlistByShareToken(shareToken);
        return ResponseEntity.ok(ApiResponse.success("Wishlist retrieved successfully", wishlist));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user wishlists", description = "Retrieve all wishlists for a user")
    public ResponseEntity<ApiResponse<List<WishlistDTO>>> getUserWishlists(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<WishlistDTO> wishlists = wishlistService.getUserWishlists(userId);
        return ResponseEntity.ok(ApiResponse.success("User wishlists retrieved successfully", wishlists));
    }

    @GetMapping("/user/{userId}/public")
    @Operation(summary = "Get user public wishlists", description = "Retrieve all public wishlists for a user")
    public ResponseEntity<ApiResponse<List<WishlistDTO>>> getUserPublicWishlists(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<WishlistDTO> wishlists = wishlistService.getUserPublicWishlists(userId);
        return ResponseEntity.ok(ApiResponse.success("User public wishlists retrieved successfully", wishlists));
    }

    @GetMapping("/user/{userId}/default")
    @Operation(summary = "Get default wishlist", description = "Retrieve the default wishlist for a user")
    public ResponseEntity<ApiResponse<WishlistDTO>> getDefaultWishlist(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        WishlistDTO wishlist = wishlistService.getDefaultWishlist(userId);
        return ResponseEntity.ok(ApiResponse.success("Default wishlist retrieved successfully", wishlist));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update wishlist", description = "Update an existing wishlist")
    public ResponseEntity<ApiResponse<WishlistDTO>> updateWishlist(
            @Parameter(description = "Wishlist ID") @PathVariable Long id,
            @Valid @RequestBody UpdateWishlistRequestDTO request) {
        WishlistDTO wishlist = wishlistService.updateWishlist(id, request);
        return ResponseEntity.ok(ApiResponse.success("Wishlist updated successfully", wishlist));
    }

    @PutMapping("/user/{userId}/default/{wishlistId}")
    @Operation(summary = "Set default wishlist", description = "Set a wishlist as default for a user")
    public ResponseEntity<ApiResponse<WishlistDTO>> setDefaultWishlist(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Wishlist ID") @PathVariable Long wishlistId) {
        WishlistDTO wishlist = wishlistService.setDefaultWishlist(userId, wishlistId);
        return ResponseEntity.ok(ApiResponse.success("Default wishlist set successfully", wishlist));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete wishlist", description = "Delete a specific wishlist")
    public ResponseEntity<ApiResponse<Void>> deleteWishlist(
            @Parameter(description = "Wishlist ID") @PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.ok(ApiResponse.success("Wishlist deleted successfully"));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to wishlist", description = "Add a product to a wishlist")
    public ResponseEntity<ApiResponse<WishlistDTO>> addItemToWishlist(@Valid @RequestBody AddToWishlistRequestDTO request) {
        WishlistDTO wishlist = wishlistService.addItemToWishlist(request);
        return ResponseEntity.ok(ApiResponse.success("Item added to wishlist successfully", wishlist));
    }

    @DeleteMapping("/{wishlistId}/items/{itemId}")
    @Operation(summary = "Remove item from wishlist", description = "Remove a product from a wishlist")
    public ResponseEntity<ApiResponse<WishlistDTO>> removeItemFromWishlist(
            @Parameter(description = "Wishlist ID") @PathVariable Long wishlistId,
            @Parameter(description = "Item ID") @PathVariable Long itemId) {
        WishlistDTO wishlist = wishlistService.removeItemFromWishlist(wishlistId, itemId);
        return ResponseEntity.ok(ApiResponse.success("Item removed from wishlist successfully", wishlist));
    }

    @PutMapping("/items/{itemId}")
    @Operation(summary = "Update wishlist item", description = "Update an item in a wishlist")
    public ResponseEntity<ApiResponse<WishlistDTO>> updateWishlistItem(
            @Parameter(description = "Item ID") @PathVariable Long itemId,
            @Valid @RequestBody UpdateWishlistItemRequestDTO request) {
        WishlistDTO wishlist = wishlistService.updateWishlistItem(itemId, request);
        return ResponseEntity.ok(ApiResponse.success("Wishlist item updated successfully", wishlist));
    }

    @PostMapping("/items/move")
    @Operation(summary = "Move item to another wishlist", description = "Move an item from one wishlist to another")
    public ResponseEntity<ApiResponse<WishlistDTO>> moveItemToAnotherWishlist(@Valid @RequestBody MoveWishlistItemRequestDTO request) {
        WishlistDTO wishlist = wishlistService.moveItemToAnotherWishlist(request);
        return ResponseEntity.ok(ApiResponse.success("Item moved to another wishlist successfully", wishlist));
    }

    @GetMapping("/{wishlistId}/items")
    @Operation(summary = "Get wishlist items", description = "Retrieve all items in a wishlist")
    public ResponseEntity<ApiResponse<List<WishlistItemDTO>>> getWishlistItems(
            @Parameter(description = "Wishlist ID") @PathVariable Long wishlistId) {
        List<WishlistItemDTO> items = wishlistService.getWishlistItems(wishlistId);
        return ResponseEntity.ok(ApiResponse.success("Wishlist items retrieved successfully", items));
    }

    @GetMapping("/{wishlistId}/items/priority/{priority}")
    @Operation(summary = "Get items by priority", description = "Retrieve items from a wishlist by priority")
    public ResponseEntity<ApiResponse<List<WishlistItemDTO>>> getItemsByPriority(
            @Parameter(description = "Wishlist ID") @PathVariable Long wishlistId,
            @Parameter(description = "Priority") @PathVariable String priority) {
        List<WishlistItemDTO> items = wishlistService.getItemsByPriority(wishlistId, priority);
        return ResponseEntity.ok(ApiResponse.success("Wishlist items retrieved successfully", items));
    }

    @PutMapping("/items/{itemId}/quantity/{quantity}")
    @Operation(summary = "Update item quantity", description = "Update the quantity of a wishlist item")
    public ResponseEntity<ApiResponse<WishlistDTO>> updateItemQuantity(
            @Parameter(description = "Item ID") @PathVariable Long itemId,
            @Parameter(description = "Quantity") @PathVariable Integer quantity) {
        WishlistDTO wishlist = wishlistService.updateItemQuantity(itemId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Item quantity updated successfully", wishlist));
    }

    @PutMapping("/items/{itemId}/priority/{priority}")
    @Operation(summary = "Update item priority", description = "Update the priority of a wishlist item")
    public ResponseEntity<ApiResponse<WishlistDTO>> updateItemPriority(
            @Parameter(description = "Item ID") @PathVariable Long itemId,
            @Parameter(description = "Priority") @PathVariable String priority) {
        WishlistDTO wishlist = wishlistService.updateItemPriority(itemId, priority);
        return ResponseEntity.ok(ApiResponse.success("Item priority updated successfully", wishlist));
    }

    @PostMapping("/{wishlistId}/clear")
    @Operation(summary = "Clear wishlist", description = "Remove all items from a wishlist")
    public ResponseEntity<ApiResponse<Void>> clearWishlist(
            @Parameter(description = "Wishlist ID") @PathVariable Long wishlistId) {
        wishlistService.clearWishlist(wishlistId);
        return ResponseEntity.ok(ApiResponse.success("Wishlist cleared successfully"));
    }

    @PostMapping("/{wishlistId}/share")
    @Operation(summary = "Generate share token", description = "Generate a share token for a wishlist")
    public ResponseEntity<ApiResponse<String>> generateShareToken(
            @Parameter(description = "Wishlist ID") @PathVariable Long wishlistId) {
        String token = wishlistService.generateShareToken(wishlistId);
        return ResponseEntity.ok(ApiResponse.success("Share token generated successfully", token));
    }

    @DeleteMapping("/{wishlistId}/share")
    @Operation(summary = "Revoke share token", description = "Revoke the share token of a wishlist")
    public ResponseEntity<ApiResponse<WishlistDTO>> revokeShareToken(
            @Parameter(description = "Wishlist ID") @PathVariable Long wishlistId) {
        WishlistDTO wishlist = wishlistService.revokeShareToken(wishlistId);
        return ResponseEntity.ok(ApiResponse.success("Share token revoked successfully", wishlist));
    }

    @GetMapping("/user/{userId}/items/price-dropped")
    @Operation(summary = "Get price dropped items", description = "Retrieve items with price drops for a user")
    public ResponseEntity<ApiResponse<List<WishlistItemDTO>>> getPriceDroppedItems(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<WishlistItemDTO> items = wishlistService.getPriceDroppedItems(userId);
        return ResponseEntity.ok(ApiResponse.success("Price dropped items retrieved successfully", items));
    }

    @PutMapping("/items/{itemId}/notify")
    @Operation(summary = "Mark item as notified", description = "Mark a wishlist item as notified")
    public ResponseEntity<ApiResponse<WishlistDTO>> markItemAsNotified(
            @Parameter(description = "Item ID") @PathVariable Long itemId) {
        WishlistDTO wishlist = wishlistService.markItemAsNotified(itemId);
        return ResponseEntity.ok(ApiResponse.success("Item marked as notified successfully", wishlist));
    }

    @GetMapping("/user/{userId}/count")
    @Operation(summary = "Count user wishlists", description = "Count wishlists for a user")
    public ResponseEntity<ApiResponse<Long>> countUserWishlists(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        Long count = wishlistService.countUserWishlists(userId);
        return ResponseEntity.ok(ApiResponse.success("Wishlist count retrieved successfully", count));
    }

    @GetMapping("/{wishlistId}/items/count")
    @Operation(summary = "Count wishlist items", description = "Count items in a wishlist")
    public ResponseEntity<ApiResponse<Long>> countWishlistItems(
            @Parameter(description = "Wishlist ID") @PathVariable Long wishlistId) {
        Long count = wishlistService.countWishlistItems(wishlistId);
        return ResponseEntity.ok(ApiResponse.success("Item count retrieved successfully", count));
    }

    @GetMapping("/user/{userId}/total-items/count")
    @Operation(summary = "Count user wishlist items", description = "Count total wishlist items for a user")
    public ResponseEntity<ApiResponse<Long>> countUserWishlistItems(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        Long count = wishlistService.countUserWishlistItems(userId);
        return ResponseEntity.ok(ApiResponse.success("Total item count retrieved successfully", count));
    }

    @GetMapping("/{wishlistId}/total-value")
    @Operation(summary = "Get wishlist total value", description = "Get total value of a wishlist")
    public ResponseEntity<ApiResponse<java.math.BigDecimal>> getWishlistTotalValue(
            @Parameter(description = "Wishlist ID") @PathVariable Long wishlistId) {
        java.math.BigDecimal total = wishlistService.getWishlistTotalValue(wishlistId);
        return ResponseEntity.ok(ApiResponse.success("Total value retrieved successfully", total));
    }

    @GetMapping("/stats/most-wished")
    @Operation(summary = "Get most wished products", description = "Retrieve most wished products across all wishlists")
    public ResponseEntity<ApiResponse<List<Object[]>>> getMostWishedProducts() {
        List<Object[]> products = wishlistService.getMostWishedProducts();
        return ResponseEntity.ok(ApiResponse.success("Most wished products retrieved successfully", products));
    }

    @GetMapping("/user/{userId}/product-ids")
    @Operation(summary = "Get user wishlisted product IDs", description = "Retrieve all wishlisted product IDs for a user")
    public ResponseEntity<ApiResponse<List<Long>>> getUserWishlistedProductIds(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<Long> productIds = wishlistService.getUserWishlistedProductIds(userId);
        return ResponseEntity.ok(ApiResponse.success("Wishlisted product IDs retrieved successfully", productIds));
    }

    @GetMapping("/user/{userId}/check-product/{productId}")
    @Operation(summary = "Check product in wishlist", description = "Check if a product is in user's wishlist")
    public ResponseEntity<ApiResponse<Boolean>> isProductInUserWishlist(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Product ID") @PathVariable Long productId) {
        Boolean exists = wishlistService.isProductInUserWishlist(userId, productId);
        return ResponseEntity.ok(ApiResponse.success("Product wishlist status retrieved successfully", exists));
    }

    @PostMapping("/user/{userId}/default/add-product")
    @Operation(summary = "Add product to default wishlist", description = "Add a product to user's default wishlist")
    public ResponseEntity<ApiResponse<WishlistDTO>> addProductToDefaultWishlist(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Product ID") @RequestParam Long productId,
            @Parameter(description = "Product Name") @RequestParam String productName,
            @Parameter(description = "Product SKU") @RequestParam(required = false) String productSku,
            @Parameter(description = "Product Image") @RequestParam(required = false) String productImage,
            @Parameter(description = "Unit Price") @RequestParam java.math.BigDecimal unitPrice) {
        WishlistDTO wishlist = wishlistService.addProductToDefaultWishlist(userId, productId, productName, productSku, productImage, unitPrice);
        return ResponseEntity.ok(ApiResponse.success("Product added to default wishlist successfully", wishlist));
    }
}
