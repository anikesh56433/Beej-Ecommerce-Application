package com.beej.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequestDTO {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    @NotBlank(message = "Type is required")
    private String type;
    
    private String category;
    private String priority = "NORMAL";
    private String actionUrl;
    private String actionText;
    private String iconUrl;
    private String imageUrl;
    private Map<String, Object> metadata;
}
