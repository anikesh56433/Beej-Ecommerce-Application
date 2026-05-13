package com.beej.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewHelpfulRequestDTO {
    
    @NotNull(message = "Review ID is required")
    private Long reviewId;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Helpful flag is required")
    private Boolean isHelpful;
    
    private String ipAddress;
    
    private String userAgent;
}
