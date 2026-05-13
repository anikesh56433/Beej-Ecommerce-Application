package com.beej.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewFilterRequestDTO {
    
    private Long productId;
    private Long userId;
    private Integer minRating;
    private Integer maxRating;
    private Boolean isVerified;
    private Boolean isApproved;
    private Boolean isFeatured;
    private LocalDate startDate;
    private LocalDate endDate;
    private String search;
    private Integer page = 0;
    private Integer limit = 20;
    private String sortBy = "createdAt";
    private String sortOrder = "desc";
}
