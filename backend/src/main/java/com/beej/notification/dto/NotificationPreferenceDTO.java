package com.beej.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferenceDTO {
    
    private Long id;
    private Long userId;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotNull(message = "Email enabled is required")
    private Boolean isEmailEnabled;
    
    @NotNull(message = "Push enabled is required")
    private Boolean isPushEnabled;
    
    @NotNull(message = "SMS enabled is required")
    private Boolean isSmsEnabled;
    
    @NotNull(message = "In-app enabled is required")
    private Boolean isInAppEnabled;
    
    private String frequency;
    private Boolean quietHoursEnabled;
    private String quietHoursStart;
    private String quietHoursEnd;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
