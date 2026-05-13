package com.beej.wishlist.repository;

import com.beej.wishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    
    List<WishlistItem> findByWishlistIdOrderByCreatedAtDesc(Long wishlistId);
    
    Optional<WishlistItem> findByWishlistIdAndProductId(Long wishlistId, Long productId);
    
    @Query("SELECT wi FROM WishlistItem wi WHERE wi.wishlist.userId = :userId AND wi.productId = :productId")
    List<WishlistItem> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    @Query("SELECT wi FROM WishlistItem wi WHERE wi.priority = :priority ORDER BY wi.createdAt DESC")
    List<WishlistItem> findByPriorityOrderByCreatedAtDesc(@Param("priority") String priority);
    
    @Query("SELECT wi FROM WishlistItem wi WHERE wi.isPriceDropped = true ORDER BY wi.createdAt DESC")
    List<WishlistItem> findPriceDroppedItems();
    
    @Query("SELECT wi FROM WishlistItem wi WHERE wi.isAvailable = false AND wi.isNotified = false ORDER BY wi.createdAt DESC")
    List<WishlistItem> findUnavailableItemsToNotify();
    
    @Query("SELECT COUNT(wi) FROM WishlistItem wi WHERE wi.wishlist.id = :wishlistId")
    Long countByWishlistId(@Param("wishlistId") Long wishlistId);
    
    @Query("SELECT SUM(wi.unitPrice * wi.quantity) FROM WishlistItem wi WHERE wi.wishlist.id = :wishlistId")
    BigDecimal sumTotalValueByWishlistId(@Param("wishlistId") Long wishlistId);
    
    void deleteByWishlistId(Long wishlistId);
    
    @Query("SELECT wi.productId, COUNT(wi) as count FROM WishlistItem wi GROUP BY wi.productId ORDER BY count DESC")
    List<Object[]> findMostWishedProducts();
    
    @Query("SELECT wi FROM WishlistItem wi WHERE wi.createdAt BETWEEN :startDate AND :endDate ORDER BY wi.createdAt DESC")
    List<WishlistItem> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                       @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(wi) FROM WishlistItem wi WHERE wi.wishlist.userId = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT DISTINCT wi.productId FROM WishlistItem wi WHERE wi.wishlist.userId = :userId")
    List<Long> findDistinctProductIdsByUserId(@Param("userId") Long userId);
}
