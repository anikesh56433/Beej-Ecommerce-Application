package com.beej.notification.mapper;

import com.beej.notification.dto.NotificationDTO;
import com.beej.notification.entity.Notification;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public NotificationDTO toNotificationDTO(Notification notification) {
        if (notification == null) {
            return null;
        }

        Map<String, Object> metadata = parseJsonToMap(notification.getMetadata());

        return NotificationDTO.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType())
                .category(notification.getCategory())
                .priority(notification.getPriority())
                .isRead(notification.getIsRead())
                .isEmailSent(notification.getIsEmailSent())
                .isPushSent(notification.getIsPushSent())
                .isSmsSent(notification.getIsSmsSent())
                .actionUrl(notification.getActionUrl())
                .actionText(notification.getActionText())
                .iconUrl(notification.getIconUrl())
                .imageUrl(notification.getImageUrl())
                .metadata(metadata)
                .expiresAt(notification.getExpiresAt())
                .readAt(notification.getReadAt())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }

    public Notification toEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        String metadataJson = mapToJson(dto.getMetadata());

        return Notification.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .type(dto.getType())
                .category(dto.getCategory())
                .priority(dto.getPriority())
                .isRead(dto.getIsRead())
                .isEmailSent(dto.getIsEmailSent())
                .isPushSent(dto.getIsPushSent())
                .isSmsSent(dto.getIsSmsSent())
                .actionUrl(dto.getActionUrl())
                .actionText(dto.getActionText())
                .iconUrl(dto.getIconUrl())
                .imageUrl(dto.getImageUrl())
                .metadata(metadataJson)
                .expiresAt(dto.getExpiresAt())
                .readAt(dto.getReadAt())
                .build();
    }

    private Map<String, Object> parseJsonToMap(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return null;
        }
    }

    private String mapToJson(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            return null;
        }
    }
}
