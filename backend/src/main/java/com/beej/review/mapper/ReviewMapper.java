package com.beej.review.mapper;

import com.beej.review.dto.ReviewDTO;
import com.beej.review.entity.Review;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {

    private final ReviewImageMapper reviewImageMapper;

    public ReviewMapper(ReviewImageMapper reviewImageMapper) {
        this.reviewImageMapper = reviewImageMapper;
    }

    public ReviewDTO toReviewDTO(Review review) {
        if (review == null) {
            return null;
        }

        return ReviewDTO.builder()
                .id(review.getId())
                .productId(review.getProductId())
                .orderId(review.getOrderId())
                .userId(review.getUserId())
                .customerName(review.getCustomerName())
                .customerEmail(review.getCustomerEmail())
                .rating(review.getRating())
                .title(review.getTitle())
                .content(review.getContent())
                .isVerified(review.getIsVerified())
                .isApproved(review.getIsApproved())
                .isFeatured(review.getIsFeatured())
                .helpfulCount(review.getHelpfulCount())
                .notHelpfulCount(review.getNotHelpfulCount())
                .reviewerIp(review.getReviewerIp())
                .reviewerUserAgent(review.getReviewerUserAgent())
                .adminResponse(review.getAdminResponse())
                .adminRespondedAt(review.getAdminRespondedAt())
                .adminRespondedBy(review.getAdminRespondedBy())
                .adminRespondedById(review.getAdminRespondedById())
                .isDeleted(review.getIsDeleted())
                .deletedAt(review.getDeletedAt())
                .deletedBy(review.getDeletedBy())
                .deletedById(review.getDeletedById())
                .images(review.getImages() != null ? 
                    review.getImages().stream()
                        .map(reviewImageMapper::toReviewImageDTO)
                        .collect(Collectors.toList()) : null)
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    public Review toEntity(ReviewDTO dto) {
        if (dto == null) {
            return null;
        }

        return Review.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .orderId(dto.getOrderId())
                .userId(dto.getUserId())
                .customerName(dto.getCustomerName())
                .customerEmail(dto.getCustomerEmail())
                .rating(dto.getRating())
                .title(dto.getTitle())
                .content(dto.getContent())
                .isVerified(dto.getIsVerified())
                .isApproved(dto.getIsApproved())
                .isFeatured(dto.getIsFeatured())
                .helpfulCount(dto.getHelpfulCount())
                .notHelpfulCount(dto.getNotHelpfulCount())
                .reviewerIp(dto.getReviewerIp())
                .reviewerUserAgent(dto.getReviewerUserAgent())
                .adminResponse(dto.getAdminResponse())
                .adminRespondedAt(dto.getAdminRespondedAt())
                .adminRespondedBy(dto.getAdminRespondedBy())
                .adminRespondedById(dto.getAdminRespondedById())
                .isDeleted(dto.getIsDeleted())
                .deletedAt(dto.getDeletedAt())
                .deletedBy(dto.getDeletedBy())
                .deletedById(dto.getDeletedById())
                .build();
    }
}
