package com.beej.admin.mapper;

import com.beej.admin.dto.AdminActivityDTO;
import com.beej.admin.entity.AdminActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AdminActivityMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AdminActivityDTO toAdminActivityDTO(AdminActivity activity) {
        if (activity == null) {
            return null;
        }

        Map<String, Object> oldValues = parseJsonToMap(activity.getOldValues());
        Map<String, Object> newValues = parseJsonToMap(activity.getNewValues());

        return AdminActivityDTO.builder()
                .id(activity.getId())
                .adminId(activity.getAdminId())
                .adminUsername(activity.getAdminUsername())
                .action(activity.getAction())
                .entityType(activity.getEntityType())
                .entityId(activity.getEntityId())
                .entityName(activity.getEntityName())
                .description(activity.getDescription())
                .ipAddress(activity.getIpAddress())
                .userAgent(activity.getUserAgent())
                .role(activity.getRole())
                .success(activity.getSuccess())
                .errorMessage(activity.getErrorMessage())
                .oldValues(oldValues)
                .newValues(newValues)
                .createdAt(activity.getCreatedAt())
                .build();
    }

    public AdminActivity toEntity(AdminActivityDTO dto) {
        if (dto == null) {
            return null;
        }

        String oldValuesJson = mapToJson(dto.getOldValues());
        String newValuesJson = mapToJson(dto.getNewValues());

        return AdminActivity.builder()
                .id(dto.getId())
                .adminId(dto.getAdminId())
                .adminUsername(dto.getAdminUsername())
                .action(dto.getAction())
                .entityType(dto.getEntityType())
                .entityId(dto.getEntityId())
                .entityName(dto.getEntityName())
                .description(dto.getDescription())
                .ipAddress(dto.getIpAddress())
                .userAgent(dto.getUserAgent())
                .role(dto.getRole())
                .success(dto.getSuccess())
                .errorMessage(dto.getErrorMessage())
                .oldValues(oldValuesJson)
                .newValues(newValuesJson)
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
