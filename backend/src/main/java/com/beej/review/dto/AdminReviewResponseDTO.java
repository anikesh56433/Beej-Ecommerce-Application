package com.beej.review.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminReviewResponseDTO {
    
    @Size(max = 2000, message = "Admin response must not exceed 2000 characters")
    private String adminResponse;
    
    private String adminRespondedBy;
    
    private Long adminRespondedById;
}
