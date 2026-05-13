package com.beej.review.repository;

import com.beej.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    Page<Review> findByProductIdOrderByCreatedAtDesc(Long productId, Pageable pageable);
    
    Page<Review> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    Page<Review> findByOrderIdOrderByCreatedAtDesc(Long orderId, Pageable pageable);
    
    Page<Review> findByIsApprovedTrueOrderByCreatedAtDesc(Pageable pageable);
    
    Page<Review> findByIsApprovedFalseOrderByCreatedAtDesc(Pageable pageable);
    
    Page<Review> findByIsApprovedTrueAndIsFeaturedTrueOrderByCreatedAtDesc(Pageable pageable);
    
    Page<Review> findByProductIdAndIsApprovedTrueOrderByCreatedAtDesc(Long productId, Pageable pageable);
    
    Page<Review> findByProductIdAndIsApprovedTrueAndRatingBetweenOrderByCreatedAtDesc(Long productId, Integer minRating, Integer maxRating, Pageable pageable);
    
    @Query("SELECT r FROM Review r WHERE r.isApproved = true AND r.isDeleted = false AND (r.title LIKE %:search% OR r.content LIKE %:search% OR r.customerName LIKE %:search%) ORDER BY r.createdAt DESC")
    Page<Review> searchApprovedReviews(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT r FROM Review r WHERE r.productId = :productId AND r.isApproved = true AND r.isDeleted = false AND (r.title LIKE %:search% OR r.content LIKE %:search% OR r.customerName LIKE %:search%) ORDER BY r.createdAt DESC")
    Page<Review> searchProductReviews(@Param("productId") Long productId, @Param("search") String search, Pageable pageable);
    
    Page<Review> findByIsVerifiedTrueOrderByCreatedAtDesc(Pageable pageable);
    
    Page<Review> findByRatingBetweenOrderByCreatedAtDesc(Integer minRating, Integer maxRating, Pageable pageable);
    
    @Query("SELECT r FROM Review r WHERE r.createdAt BETWEEN :startDate AND :endDate ORDER BY r.createdAt DESC")
    Page<Review> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                @Param("endDate") LocalDateTime endDate, 
                                Pageable pageable);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productId = :productId AND r.isApproved = true AND r.isDeleted = false")
    Double getAverageRatingByProductId(@Param("productId") Long productId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.productId = :productId AND r.isApproved = true AND r.isDeleted = false")
    Long countApprovedReviewsByProductId(@Param("productId") Long productId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.productId = :productId AND r.rating = :rating AND r.isApproved = true AND r.isDeleted = false")
    Long countReviewsByProductIdAndRating(@Param("productId") Long productId, @Param("rating") Integer rating);
    
    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.productId = :productId AND r.isApproved = true AND r.isDeleted = false GROUP BY r.rating ORDER BY r.rating")
    List<Object[]> getRatingDistributionByProductId(@Param("productId") Long productId);
    
    Optional<Review> findByUserIdAndProductId(Long userId, Long productId);
    
    Optional<Review> findByUserIdAndOrderId(Long userId, Long orderId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.isApproved = false AND r.isDeleted = false")
    Long countPendingReviews();
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.isApproved = true AND r.isDeleted = false")
    Long countApprovedReviews();
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.isDeleted = true")
    Long countDeletedReviews();
    
    List<Review> findTop10ByIsApprovedTrueOrderByCreatedAtDesc();
    
    List<Review> findTop10ByIsApprovedTrueAndIsFeaturedTrueOrderByCreatedAtDesc();
    
    @Query("SELECT DISTINCT r.productId FROM Review r WHERE r.isApproved = true AND r.isDeleted = false")
    List<Long> findDistinctProductIds();
}
