package com.beej.admin.dto;

import com.beej.common.enums.RoleType;
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
public class AdminActivityDTO {
    
    private Long id;
    private Long adminId;
    private String adminUsername;
    private String action;
    private String entityType;
    private Long entityId;
    private String entityName;
    private String description;
    private String ipAddress;
    private String userAgent;
    private RoleType role;
    private Boolean success;
    private String errorMessage;
    private Map<String, Object> oldValues;
    private Map<String, Object> newValues;
    private LocalDateTime createdAt;
}
