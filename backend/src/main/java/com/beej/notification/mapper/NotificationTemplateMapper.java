package com.beej.notification.mapper;

import com.beej.notification.dto.NotificationTemplateDTO;
import com.beej.notification.entity.NotificationTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationTemplateMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public NotificationTemplateDTO toNotificationTemplateDTO(NotificationTemplate template) {
        if (template == null) {
            return null;
        }

        List<String> variables = parseJsonToList(template.getVariables());

        return NotificationTemplateDTO.builder()
                .id(template.getId())
                .name(template.getName())
                .title(template.getTitle())
                .message(template.getMessage())
                .type(template.getType())
                .category(template.getCategory())
                .priority(template.getPriority())
                .isActive(template.getIsActive())
                .isEmailEnabled(template.getIsEmailEnabled())
                .isPushEnabled(template.getIsPushEnabled())
                .isSmsEnabled(template.getIsSmsEnabled())
                .emailSubject(template.getEmailSubject())
                .emailTemplate(template.getEmailTemplate())
                .smsTemplate(template.getSmsTemplate())
                .pushTemplate(template.getPushTemplate())
                .variables(variables)
                .createdAt(template.getCreatedAt())
                .updatedAt(template.getUpdatedAt())
                .build();
    }

    public NotificationTemplate toEntity(NotificationTemplateDTO dto) {
        if (dto == null) {
            return null;
        }

        String variablesJson = listToJson(dto.getVariables());

        return NotificationTemplate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .type(dto.getType())
                .category(dto.getCategory())
                .priority(dto.getPriority())
                .isActive(dto.getIsActive())
                .isEmailEnabled(dto.getIsEmailEnabled())
                .isPushEnabled(dto.getIsPushEnabled())
                .isSmsEnabled(dto.getIsSmsEnabled())
                .emailSubject(dto.getEmailSubject())
                .emailTemplate(dto.getEmailTemplate())
                .smsTemplate(dto.getSmsTemplate())
                .pushTemplate(dto.getPushTemplate())
                .variables(variablesJson)
                .build();
    }

    private List<String> parseJsonToList(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return null;
        }
    }

    private String listToJson(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            return null;
        }
    }
}
