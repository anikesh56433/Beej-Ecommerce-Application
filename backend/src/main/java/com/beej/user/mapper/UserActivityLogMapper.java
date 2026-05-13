package com.beej.user.mapper;

import com.beej.user.dto.UserActivityLogDTO;
import com.beej.user.entity.UserActivityLog;
import org.springframework.stereotype.Component;

@Component
public class UserActivityLogMapper {

    public UserActivityLogDTO toUserActivityLogDTO(UserActivityLog activityLog) {
        if (activityLog == null) {
            return null;
        }

        return UserActivityLogDTO.builder()
                .id(activityLog.getId())
                .userId(activityLog.getUserId())
                .activityType(activityLog.getActivityType())
                .activityDescription(activityLog.getActivityDescription())
                .ipAddress(activityLog.getIpAddress())
                .userAgent(activityLog.getUserAgent())
                .deviceInfo(activityLog.getDeviceInfo())
                .browserInfo(activityLog.getBrowserInfo())
                .osInfo(activityLog.getOsInfo())
                .location(activityLog.getLocation())
                .referenceId(activityLog.getReferenceId())
                .referenceType(activityLog.getReferenceType())
                .status(activityLog.getStatus())
                .metadata(activityLog.getMetadata())
                .createdAt(activityLog.getCreatedAt())
                .build();
    }

    public UserActivityLog toEntity(UserActivityLogDTO dto) {
        if (dto == null) {
            return null;
        }

        return UserActivityLog.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .activityType(dto.getActivityType())
                .activityDescription(dto.getActivityDescription())
                .ipAddress(dto.getIpAddress())
                .userAgent(dto.getUserAgent())
                .deviceInfo(dto.getDeviceInfo())
                .browserInfo(dto.getBrowserInfo())
                .osInfo(dto.getOsInfo())
                .location(dto.getLocation())
                .referenceId(dto.getReferenceId())
                .referenceType(dto.getReferenceType())
                .status(dto.getStatus())
                .metadata(dto.getMetadata())
                .build();
    }
}
