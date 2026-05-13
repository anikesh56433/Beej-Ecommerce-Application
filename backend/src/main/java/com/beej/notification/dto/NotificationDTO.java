package com.beej.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    
    private Long id;
    private Long userId;
    private String title;
    private String message;
    private String type;
    private String category;
    private String priority;
    private Boolean isRead;
    private Boolean isEmailSent;
    private Boolean isPushSent;
    private Boolean isSmsSent;
    private String actionUrl;
    private String actionText;
    private String iconUrl;
    private String imageUrl;
    private Map<String, Object> metadata;
    private LocalDateTime expiresAt;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
