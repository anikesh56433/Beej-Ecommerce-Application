package com.beej.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    
    private Long id;
    private Long productId;
    private Long orderId;
    private Long userId;
    private String customerName;
    private String customerEmail;
    private Integer rating;
    private String title;
    private String content;
    private Boolean isVerified;
    private Boolean isApproved;
    private Boolean isFeatured;
    private Integer helpfulCount;
    private Integer notHelpfulCount;
    private String reviewerIp;
    private String reviewerUserAgent;
    private String adminResponse;
    private LocalDateTime adminRespondedAt;
    private String adminRespondedBy;
    private Long adminRespondedById;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    private String deletedBy;
    private Long deletedById;
    private List<ReviewImageDTO> images;
    private Boolean currentUserHelpful;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
