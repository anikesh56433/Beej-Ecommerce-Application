package com.beej.review.mapper;

import com.beej.review.entity.Review;
import com.beej.review.entity.ReviewHelpful;
import org.springframework.stereotype.Component;

@Component
public class ReviewHelpfulMapper {

    public ReviewHelpful toEntity(Review review, Long userId, Boolean isHelpful, String ipAddress, String userAgent) {
        return ReviewHelpful.builder()
                .review(review)
                .userId(userId)
                .isHelpful(isHelpful)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .build();
    }
}
