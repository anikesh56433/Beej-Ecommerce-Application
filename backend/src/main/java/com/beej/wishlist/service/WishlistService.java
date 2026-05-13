package com.beej.wishlist.service;

import com.beej.wishlist.dto.*;

import java.util.List;

public interface WishlistService {
    
    WishlistDTO createWishlist(CreateWishlistRequestDTO request);
    
    WishlistDTO getWishlistById(Long id);
    
    WishlistDTO getWishlistByShareToken(String shareToken);
    
    List<WishlistDTO> getUserWishlists(Long userId);
    
    List<WishlistDTO> getUserPublicWishlists(Long userId);
    
    WishlistDTO getDefaultWishlist(Long userId);
    
    WishlistDTO updateWishlist(Long id, UpdateWishlistRequestDTO request);
    
    WishlistDTO setDefaultWishlist(Long userId, Long wishlistId);
    
    void deleteWishlist(Long id);
    
    WishlistDTO addItemToWishlist(AddToWishlistRequestDTO request);
    
    WishlistDTO removeItemFromWishlist(Long wishlistId, Long itemId);
    
    WishlistDTO updateWishlistItem(Long itemId, UpdateWishlistItemRequestDTO request);
    
    WishlistDTO moveItemToAnotherWishlist(MoveWishlistItemRequestDTO request);
    
    List<WishlistItemDTO> getWishlistItems(Long wishlistId);
    
    List<WishlistItemDTO> getItemsByPriority(Long wishlistId, String priority);
    
    WishlistDTO updateItemQuantity(Long itemId, Integer quantity);
    
    WishlistDTO updateItemPriority(Long itemId, String priority);
    
    List<WishlistItemDTO> getPriceDroppedItems(Long userId);
    
    List<WishlistItemDTO> getUnavailableItemsToNotify(Long userId);
    
    WishlistDTO markItemAsNotified(Long itemId);
    
    Long countUserWishlists(Long userId);
    
    Long countWishlistItems(Long wishlistId);
    
    Long countUserWishlistItems(Long userId);
    
    java.math.BigDecimal getWishlistTotalValue(Long wishlistId);
    
    List<Object[]> getMostWishedProducts();
    
    List<Long> getUserWishlistedProductIds(Long userId);
    
    Boolean isProductInUserWishlist(Long userId, Long productId);
    
    WishlistDTO addProductToDefaultWishlist(Long userId, Long productId, String productName, 
                                             String productSku, String productImage, 
                                             java.math.BigDecimal unitPrice);
    
    void clearWishlist(Long wishlistId);
    
    String generateShareToken(Long wishlistId);
    
    WishlistDTO revokeShareToken(Long wishlistId);
    
    void updateWishlistTotals(Long wishlistId);
}
