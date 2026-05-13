package com.beej.review.controller;

import com.beej.review.dto.*;
import com.beej.review.service.ReviewService;
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
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Management", description = "APIs for managing product reviews")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Create review", description = "Create a new product review")
    public ResponseEntity<ApiResponse<ReviewDTO>> createReview(@Valid @RequestBody CreateReviewRequestDTO request) {
        ReviewDTO review = reviewService.createReview(request);
        return ResponseEntity.ok(ApiResponse.success("Review created successfully", review));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID", description = "Retrieve a specific review by its ID")
    public ResponseEntity<ApiResponse<ReviewDTO>> getReviewById(
            @Parameter(description = "Review ID") @PathVariable Long id) {
        ReviewDTO review = reviewService.getReviewById(id);
        return ResponseEntity.ok(ApiResponse.success("Review retrieved successfully", review));
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get product reviews", description = "Retrieve all reviews for a specific product")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getProductReviews(
            @Parameter(description = "Product ID") @PathVariable Long productId,
            @Parameter(description = "Limit number of results") @RequestParam(required = false) Integer limit) {
        List<ReviewDTO> reviews = limit != null 
                ? reviewService.getProductReviews(productId, limit)
                : reviewService.getProductReviews(productId);
        return ResponseEntity.ok(ApiResponse.success("Product reviews retrieved successfully", reviews));
    }

    @GetMapping("/product/{productId}/rating")
    @Operation(summary = "Get product reviews by rating", description = "Retrieve reviews for a product within a rating range")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getProductReviewsByRating(
            @Parameter(description = "Product ID") @PathVariable Long productId,
            @Parameter(description = "Minimum rating") @RequestParam Integer minRating,
            @Parameter(description = "Maximum rating") @RequestParam Integer maxRating) {
        List<ReviewDTO> reviews = reviewService.getProductReviewsByRating(productId, minRating, maxRating);
        return ResponseEntity.ok(ApiResponse.success("Product reviews retrieved successfully", reviews));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user reviews", description = "Retrieve all reviews by a specific user")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getUserReviews(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<ReviewDTO> reviews = reviewService.getUserReviews(userId);
        return ResponseEntity.ok(ApiResponse.success("User reviews retrieved successfully", reviews));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get order reviews", description = "Retrieve all reviews for a specific order")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getOrderReviews(
            @Parameter(description = "Order ID") @PathVariable Long orderId) {
        List<ReviewDTO> reviews = reviewService.getOrderReviews(orderId);
        return ResponseEntity.ok(ApiResponse.success("Order reviews retrieved successfully", reviews));
    }

    @GetMapping("/approved")
    @Operation(summary = "Get approved reviews", description = "Retrieve all approved reviews")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getApprovedReviews() {
        List<ReviewDTO> reviews = reviewService.getApprovedReviews();
        return ResponseEntity.ok(ApiResponse.success("Approved reviews retrieved successfully", reviews));
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured reviews", description = "Retrieve all featured reviews")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getFeaturedReviews() {
        List<ReviewDTO> reviews = reviewService.getFeaturedReviews();
        return ResponseEntity.ok(ApiResponse.success("Featured reviews retrieved successfully", reviews));
    }

    @GetMapping("/pending")
    @Operation(summary = "Get pending reviews", description = "Retrieve all pending reviews")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getPendingReviews() {
        List<ReviewDTO> reviews = reviewService.getPendingReviews();
        return ResponseEntity.ok(ApiResponse.success("Pending reviews retrieved successfully", reviews));
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter reviews", description = "Filter reviews with multiple criteria")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> filterReviews(@Valid @RequestBody ReviewFilterRequestDTO filter) {
        List<ReviewDTO> reviews = reviewService.filterReviews(filter);
        return ResponseEntity.ok(ApiResponse.success("Reviews filtered successfully", reviews));
    }

    @GetMapping("/search")
    @Operation(summary = "Search reviews", description = "Search reviews by content, title, or customer name")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> searchReviews(
            @Parameter(description = "Search query") @RequestParam String search) {
        List<ReviewDTO> reviews = reviewService.searchReviews(search);
        return ResponseEntity.ok(ApiResponse.success("Reviews searched successfully", reviews));
    }

    @GetMapping("/product/{productId}/search")
    @Operation(summary = "Search product reviews", description = "Search reviews within a specific product")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> searchProductReviews(
            @Parameter(description = "Product ID") @PathVariable Long productId,
            @Parameter(description = "Search query") @RequestParam String search) {
        List<ReviewDTO> reviews = reviewService.searchProductReviews(productId, search);
        return ResponseEntity.ok(ApiResponse.success("Product reviews searched successfully", reviews));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update review", description = "Update an existing review")
    public ResponseEntity<ApiResponse<ReviewDTO>> updateReview(
            @Parameter(description = "Review ID") @PathVariable Long id,
            @Valid @RequestBody UpdateReviewRequestDTO request) {
        ReviewDTO review = reviewService.updateReview(id, request);
        return ResponseEntity.ok(ApiResponse.success("Review updated successfully", review));
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve review", description = "Approve a pending review")
    public ResponseEntity<ApiResponse<ReviewDTO>> approveReview(
            @Parameter(description = "Review ID") @PathVariable Long id) {
        ReviewDTO review = reviewService.approveReview(id);
        return ResponseEntity.ok(ApiResponse.success("Review approved successfully", review));
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject review", description = "Reject a pending review")
    public ResponseEntity<ApiResponse<ReviewDTO>> rejectReview(
            @Parameter(description = "Review ID") @PathVariable Long id) {
        ReviewDTO review = reviewService.rejectReview(id);
        return ResponseEntity.ok(ApiResponse.success("Review rejected successfully", review));
    }

    @PutMapping("/{id}/feature")
    @Operation(summary = "Feature review", description = "Mark a review as featured")
    public ResponseEntity<ApiResponse<ReviewDTO>> featureReview(
            @Parameter(description = "Review ID") @PathVariable Long id) {
        ReviewDTO review = reviewService.featureReview(id);
        return ResponseEntity.ok(ApiResponse.success("Review featured successfully", review));
    }

    @PutMapping("/{id}/unfeature")
    @Operation(summary = "Unfeature review", description = "Remove featured status from a review")
    public ResponseEntity<ApiResponse<ReviewDTO>> unfeatureReview(
            @Parameter(description = "Review ID") @PathVariable Long id) {
        ReviewDTO review = reviewService.unfeatureReview(id);
        return ResponseEntity.ok(ApiResponse.success("Review unfeatured successfully", review));
    }

    @PutMapping("/{id}/verify")
    @Operation(summary = "Verify review", description = "Mark a review as verified")
    public ResponseEntity<ApiResponse<ReviewDTO>> verifyReview(
            @Parameter(description = "Review ID") @PathVariable Long id) {
        ReviewDTO review = reviewService.verifyReview(id);
        return ResponseEntity.ok(ApiResponse.success("Review verified successfully", review));
    }

    @PutMapping("/{id}/admin-response")
    @Operation(summary = "Add admin response", description = "Add an admin response to a review")
    public ResponseEntity<ApiResponse<ReviewDTO>> addAdminResponse(
            @Parameter(description = "Review ID") @PathVariable Long id,
            @Valid @RequestBody AdminReviewResponseDTO response) {
        ReviewDTO review = reviewService.addAdminResponse(id, response);
        return ResponseEntity.ok(ApiResponse.success("Admin response added successfully", review));
    }

    @PostMapping("/helpful")
    @Operation(summary = "Mark review as helpful", description = "Mark a review as helpful by a user")
    public ResponseEntity<ApiResponse<ReviewDTO>> markAsHelpful(@Valid @RequestBody ReviewHelpfulRequestDTO request) {
        ReviewDTO review = reviewService.markAsHelpful(request);
        return ResponseEntity.ok(ApiResponse.success("Review marked as helpful successfully", review));
    }

    @PostMapping("/not-helpful")
    @Operation(summary = "Mark review as not helpful", description = "Mark a review as not helpful by a user")
    public ResponseEntity<ApiResponse<ReviewDTO>> markAsNotHelpful(@Valid @RequestBody ReviewHelpfulRequestDTO request) {
        ReviewDTO review = reviewService.markAsNotHelpful(request);
        return ResponseEntity.ok(ApiResponse.success("Review marked as not helpful successfully", review));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review", description = "Delete a specific review")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @Parameter(description = "Review ID") @PathVariable Long id,
            @Parameter(description = "Deleted by") @RequestParam String deletedBy,
            @Parameter(description = "Deleted by ID") @RequestParam(required = false) Long deletedById) {
        reviewService.deleteReview(id, deletedBy, deletedById);
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully"));
    }

    @PutMapping("/{id}/soft-delete")
    @Operation(summary = "Soft delete review", description = "Soft delete a specific review")
    public ResponseEntity<ApiResponse<Void>> softDeleteReview(
            @Parameter(description = "Review ID") @PathVariable Long id,
            @Parameter(description = "Deleted by") @RequestParam String deletedBy,
            @Parameter(description = "Deleted by ID") @RequestParam(required = false) Long deletedById) {
        reviewService.softDeleteReview(id, deletedBy, deletedById);
        return ResponseEntity.ok(ApiResponse.success("Review soft deleted successfully"));
    }

    @GetMapping("/product/{productId}/average-rating")
    @Operation(summary = "Get average rating", description = "Get average rating for a product")
    public ResponseEntity<ApiResponse<Double>> getAverageRatingByProductId(
            @Parameter(description = "Product ID") @PathVariable Long productId) {
        Double averageRating = reviewService.getAverageRatingByProductId(productId);
        return ResponseEntity.ok(ApiResponse.success("Average rating retrieved successfully", averageRating));
    }

    @GetMapping("/product/{productId}/count")
    @Operation(summary = "Count approved reviews", description = "Count approved reviews for a product")
    public ResponseEntity<ApiResponse<Long>> countApprovedReviewsByProductId(
            @Parameter(description = "Product ID") @PathVariable Long productId) {
        Long count = reviewService.countApprovedReviewsByProductId(productId);
        return ResponseEntity.ok(ApiResponse.success("Review count retrieved successfully", count));
    }

    @GetMapping("/product/{productId}/rating/{rating}/count")
    @Operation(summary = "Count reviews by rating", description = "Count reviews for a product with specific rating")
    public ResponseEntity<ApiResponse<Long>> countReviewsByProductIdAndRating(
            @Parameter(description = "Product ID") @PathVariable Long productId,
            @Parameter(description = "Rating") @PathVariable Integer rating) {
        Long count = reviewService.countReviewsByProductIdAndRating(productId, rating);
        return ResponseEntity.ok(ApiResponse.success("Review count retrieved successfully", count));
    }

    @GetMapping("/product/{productId}/rating-distribution")
    @Operation(summary = "Get rating distribution", description = "Get rating distribution for a product")
    public ResponseEntity<ApiResponse<List<Object[]>>> getRatingDistributionByProductId(
            @Parameter(description = "Product ID") @PathVariable Long productId) {
        List<Object[]> distribution = reviewService.getRatingDistributionByProductId(productId);
        return ResponseEntity.ok(ApiResponse.success("Rating distribution retrieved successfully", distribution));
    }

    @GetMapping("/stats/pending/count")
    @Operation(summary = "Count pending reviews", description = "Get count of pending reviews")
    public ResponseEntity<ApiResponse<Long>> countPendingReviews() {
        Long count = reviewService.countPendingReviews();
        return ResponseEntity.ok(ApiResponse.success("Pending review count retrieved successfully", count));
    }

    @GetMapping("/stats/approved/count")
    @Operation(summary = "Count approved reviews", description = "Get count of approved reviews")
    public ResponseEntity<ApiResponse<Long>> countApprovedReviews() {
        Long count = reviewService.countApprovedReviews();
        return ResponseEntity.ok(ApiResponse.success("Approved review count retrieved successfully", count));
    }

    @GetMapping("/stats/deleted/count")
    @Operation(summary = "Count deleted reviews", description = "Get count of deleted reviews")
    public ResponseEntity<ApiResponse<Long>> countDeletedReviews() {
        Long count = reviewService.countDeletedReviews();
        return ResponseEntity.ok(ApiResponse.success("Deleted review count retrieved successfully", count));
    }

    @GetMapping("/products")
    @Operation(summary = "Get distinct product IDs", description = "Get all distinct product IDs with reviews")
    public ResponseEntity<ApiResponse<List<Long>>> getDistinctProductIds() {
        List<Long> productIds = reviewService.getDistinctProductIds();
        return ResponseEntity.ok(ApiResponse.success("Product IDs retrieved successfully", productIds));
    }
}
