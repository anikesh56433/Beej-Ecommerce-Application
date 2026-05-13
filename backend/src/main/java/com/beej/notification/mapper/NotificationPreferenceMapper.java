package com.beej.notification.mapper;

import com.beej.notification.dto.NotificationPreferenceDTO;
import com.beej.notification.entity.NotificationPreference;
import org.springframework.stereotype.Component;

@Component
public class NotificationPreferenceMapper {

    public NotificationPreferenceDTO toNotificationPreferenceDTO(NotificationPreference preference) {
        if (preference == null) {
            return null;
        }

        return NotificationPreferenceDTO.builder()
                .id(preference.getId())
                .userId(preference.getUserId())
                .category(preference.getCategory())
                .isEmailEnabled(preference.getIsEmailEnabled())
                .isPushEnabled(preference.getIsPushEnabled())
                .isSmsEnabled(preference.getIsSmsEnabled())
                .isInAppEnabled(preference.getIsInAppEnabled())
                .frequency(preference.getFrequency())
                .quietHoursEnabled(preference.getQuietHoursEnabled())
                .quietHoursStart(preference.getQuietHoursStart())
                .quietHoursEnd(preference.getQuietHoursEnd())
                .createdAt(preference.getCreatedAt())
                .updatedAt(preference.getUpdatedAt())
                .build();
    }

    public NotificationPreference toEntity(NotificationPreferenceDTO dto) {
        if (dto == null) {
            return null;
        }

        return NotificationPreference.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .category(dto.getCategory())
                .isEmailEnabled(dto.getIsEmailEnabled())
                .isPushEnabled(dto.getIsPushEnabled())
                .isSmsEnabled(dto.getIsSmsEnabled())
                .isInAppEnabled(dto.getIsInAppEnabled())
                .frequency(dto.getFrequency())
                .quietHoursEnabled(dto.getQuietHoursEnabled())
                .quietHoursStart(dto.getQuietHoursStart())
                .quietHoursEnd(dto.getQuietHoursEnd())
                .build();
    }
}
