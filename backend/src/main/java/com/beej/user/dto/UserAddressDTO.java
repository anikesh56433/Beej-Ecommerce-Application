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
public class UserAddressDTO {
    
    private Long id;
    private Long userId;
    private String addressType;
    private Boolean isDefault;
    private String recipientName;
    private String recipientPhone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String countryCode;
    private Double latitude;
    private Double longitude;
    private String deliveryInstructions;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
