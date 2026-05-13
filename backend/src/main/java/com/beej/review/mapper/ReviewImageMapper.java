package com.beej.review.mapper;

import com.beej.review.dto.ReviewImageDTO;
import com.beej.review.entity.ReviewImage;
import org.springframework.stereotype.Component;

@Component
public class ReviewImageMapper {

    public ReviewImageDTO toReviewImageDTO(ReviewImage image) {
        if (image == null) {
            return null;
        }

        return ReviewImageDTO.builder()
                .id(image.getId())
                .reviewId(image.getReview() != null ? image.getReview().getId() : null)
                .imageUrl(image.getImageUrl())
                .imageAlt(image.getImageAlt())
                .imageOrder(image.getImageOrder())
                .isPrimary(image.getIsPrimary())
                .createdAt(image.getCreatedAt())
                .updatedAt(image.getUpdatedAt())
                .build();
    }

    public ReviewImage toEntity(ReviewImageDTO dto) {
        if (dto == null) {
            return null;
        }

        ReviewImage image = ReviewImage.builder()
                .id(dto.getId())
                .imageUrl(dto.getImageUrl())
                .imageAlt(dto.getImageAlt())
                .imageOrder(dto.getImageOrder())
                .isPrimary(dto.getIsPrimary())
                .build();

        return image;
    }
}
