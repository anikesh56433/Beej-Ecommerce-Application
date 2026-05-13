package com.beej.user.mapper;

import com.beej.user.dto.UserAddressDTO;
import com.beej.user.entity.UserAddress;
import org.springframework.stereotype.Component;

@Component
public class UserAddressMapper {

    public UserAddressDTO toUserAddressDTO(UserAddress address) {
        if (address == null) {
            return null;
        }

        return UserAddressDTO.builder()
                .id(address.getId())
                .userId(address.getUserId())
                .addressType(address.getAddressType())
                .isDefault(address.getIsDefault())
                .recipientName(address.getRecipientName())
                .recipientPhone(address.getRecipientPhone())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .countryCode(address.getCountryCode())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .deliveryInstructions(address.getDeliveryInstructions())
                .isActive(address.getIsActive())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }

    public UserAddress toEntity(UserAddressDTO dto) {
        if (dto == null) {
            return null;
        }

        return UserAddress.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .addressType(dto.getAddressType())
                .isDefault(dto.getIsDefault())
                .recipientName(dto.getRecipientName())
                .recipientPhone(dto.getRecipientPhone())
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .countryCode(dto.getCountryCode())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .deliveryInstructions(dto.getDeliveryInstructions())
                .isActive(dto.getIsActive())
                .build();
    }
}
