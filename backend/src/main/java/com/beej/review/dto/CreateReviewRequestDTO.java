package com.beej.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequestDTO {
    
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    private Long orderId;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @Size(max = 255, message = "Customer name must not exceed 255 characters")
    private String customerName;
    
    @Size(max = 255, message = "Customer email must not exceed 255 characters")
    private String customerEmail;
    
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;
    
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    @Size(max = 5000, message = "Content must not exceed 5000 characters")
    private String content;
    
    private List<String> imageUrls;
    
    private String reviewerIp;
    
    private String reviewerUserAgent;
}
