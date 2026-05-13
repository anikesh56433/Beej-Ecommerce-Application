package com.beej.notification.dto;

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
public class NotificationTemplateDTO {
    
    private Long id;
    private String name;
    private String title;
    private String message;
    private String type;
    private String category;
    private String priority;
    private Boolean isActive;
    private Boolean isEmailEnabled;
    private Boolean isPushEnabled;
    private Boolean isSmsEnabled;
    private String emailSubject;
    private String emailTemplate;
    private String smsTemplate;
    private String pushTemplate;
    private List<String> variables;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
