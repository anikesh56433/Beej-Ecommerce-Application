package com.beej.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityLogDTO {
    
    private Long id;
    private Long userId;
    private String activityType;
    private String activityDescription;
    private String ipAddress;
    private String userAgent;
    private String deviceInfo;
    private String browserInfo;
    private String osInfo;
    private String location;
    private Long referenceId;
    private String referenceType;
    private String status;
    private String metadata;
    private LocalDateTime createdAt;
}
