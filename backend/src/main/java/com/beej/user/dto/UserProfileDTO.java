package com.beej.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String displayName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String bio;
    private String profilePictureUrl;
    private String coverPhotoUrl;
    private String website;
    private String occupation;
    private String company;
    private String location;
    private String timezone;
    private String language;
    private String currency;
    private Boolean newsletterSubscribed;
    private Boolean marketingEmailsEnabled;
    private Boolean orderNotificationsEnabled;
    private Boolean promotionNotificationsEnabled;
    private Boolean twoFactorEnabled;
    private LocalDateTime lastLoginAt;
    private String lastLoginIp;
    private Integer loginCount;
    private String accountStatus;
    private List<UserAddressDTO> addresses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
