package com.beej.review.service;

import com.beej.review.dto.*;

import java.util.List;

public interface ReviewService {
    
    ReviewDTO createReview(CreateReviewRequestDTO request);
    
    ReviewDTO getReviewById(Long id);
    
    List<ReviewDTO> getProductReviews(Long productId);
    
    List<ReviewDTO> getProductReviews(Long productId, int limit);
    
    List<ReviewDTO> getProductReviewsByRating(Long productId, Integer minRating, Integer maxRating);
    
    List<ReviewDTO> getUserReviews(Long userId);
    
    List<ReviewDTO> getOrderReviews(Long orderId);
    
    List<ReviewDTO> getApprovedReviews();
    
    List<ReviewDTO> getFeaturedReviews();
    
    List<ReviewDTO> getPendingReviews();
    
    List<ReviewDTO> filterReviews(ReviewFilterRequestDTO filter);
    
    List<ReviewDTO> searchReviews(String search);
    
    List<ReviewDTO> searchProductReviews(Long productId, String search);
    
    ReviewDTO updateReview(Long id, UpdateReviewRequestDTO request);
    
    ReviewDTO approveReview(Long id);
    
    ReviewDTO rejectReview(Long id);
    
    ReviewDTO featureReview(Long id);
    
    ReviewDTO unfeatureReview(Long id);
    
    ReviewDTO verifyReview(Long id);
    
    ReviewDTO addAdminResponse(Long id, AdminReviewResponseDTO response);
    
    ReviewDTO markAsHelpful(ReviewHelpfulRequestDTO request);
    
    ReviewDTO markAsNotHelpful(ReviewHelpfulRequestDTO request);
    
    void deleteReview(Long id, String deletedBy, Long deletedById);
    
    void softDeleteReview(Long id, String deletedBy, Long deletedById);
    
    Double getAverageRatingByProductId(Long productId);
    
    Long countApprovedReviewsByProductId(Long productId);
    
    Long countReviewsByProductIdAndRating(Long productId, Integer rating);
    
    List<Object[]> getRatingDistributionByProductId(Long productId);
    
    Long countPendingReviews();
    
    Long countApprovedReviews();
    
    Long countDeletedReviews();
    
    List<Long> getDistinctProductIds();
}
