package com.beej.user.mapper;

import com.beej.user.dto.UserProfileDTO;
import com.beej.user.entity.UserProfile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserProfileMapper {

    private final UserAddressMapper userAddressMapper;

    public UserProfileMapper(UserAddressMapper userAddressMapper) {
        this.userAddressMapper = userAddressMapper;
    }

    public UserProfileDTO toUserProfileDTO(UserProfile profile) {
        if (profile == null) {
            return null;
        }

        return UserProfileDTO.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .username(profile.getUsername())
                .email(profile.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .displayName(profile.getDisplayName())
                .phoneNumber(profile.getPhoneNumber())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .bio(profile.getBio())
                .profilePictureUrl(profile.getProfilePictureUrl())
                .coverPhotoUrl(profile.getCoverPhotoUrl())
                .website(profile.getWebsite())
                .occupation(profile.getOccupation())
                .company(profile.getCompany())
                .location(profile.getLocation())
                .timezone(profile.getTimezone())
                .language(profile.getLanguage())
                .currency(profile.getCurrency())
                .newsletterSubscribed(profile.getNewsletterSubscribed())
                .marketingEmailsEnabled(profile.getMarketingEmailsEnabled())
                .orderNotificationsEnabled(profile.getOrderNotificationsEnabled())
                .promotionNotificationsEnabled(profile.getPromotionNotificationsEnabled())
                .twoFactorEnabled(profile.getTwoFactorEnabled())
                .lastLoginAt(profile.getLastLoginAt())
                .lastLoginIp(profile.getLastLoginIp())
                .loginCount(profile.getLoginCount())
                .accountStatus(profile.getAccountStatus())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

    public UserProfileDTO toUserProfileDTOWithAddresses(UserProfile profile, List<com.beej.user.entity.UserAddress> addresses) {
        UserProfileDTO dto = toUserProfileDTO(profile);
        if (dto != null && addresses != null) {
            dto.setAddresses(addresses.stream()
                    .map(userAddressMapper::toUserAddressDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public UserProfile toEntity(UserProfileDTO dto) {
        if (dto == null) {
            return null;
        }

        return UserProfile.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .displayName(dto.getDisplayName())
                .phoneNumber(dto.getPhoneNumber())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .bio(dto.getBio())
                .profilePictureUrl(dto.getProfilePictureUrl())
                .coverPhotoUrl(dto.getCoverPhotoUrl())
                .website(dto.getWebsite())
                .occupation(dto.getOccupation())
                .company(dto.getCompany())
                .location(dto.getLocation())
                .timezone(dto.getTimezone())
                .language(dto.getLanguage())
                .currency(dto.getCurrency())
                .newsletterSubscribed(dto.getNewsletterSubscribed())
                .marketingEmailsEnabled(dto.getMarketingEmailsEnabled())
                .orderNotificationsEnabled(dto.getOrderNotificationsEnabled())
                .promotionNotificationsEnabled(dto.getPromotionNotificationsEnabled())
                .twoFactorEnabled(dto.getTwoFactorEnabled())
                .lastLoginAt(dto.getLastLoginAt())
                .lastLoginIp(dto.getLastLoginIp())
                .loginCount(dto.getLoginCount())
                .accountStatus(dto.getAccountStatus())
                .build();
    }
}
