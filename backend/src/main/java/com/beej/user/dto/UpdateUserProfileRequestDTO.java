package com.beej.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequestDTO {
    
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;
    
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;
    
    @Size(max = 150, message = "Display name must not exceed 150 characters")
    private String displayName;
    
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;
    
    private LocalDate dateOfBirth;
    
    @Size(max = 20, message = "Gender must not exceed 20 characters")
    private String gender;
    
    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    private String bio;
    
    private String profilePictureUrl;
    
    private String coverPhotoUrl;
    
    @Size(max = 255, message = "Website must not exceed 255 characters")
    private String website;
    
    @Size(max = 100, message = "Occupation must not exceed 100 characters")
    private String occupation;
    
    @Size(max = 100, message = "Company must not exceed 100 characters")
    private String company;
    
    @Size(max = 255, message = "Location must not exceed 255 characters")
    private String location;
    
    @Size(max = 50, message = "Timezone must not exceed 50 characters")
    private String timezone;
    
    @Size(max = 10, message = "Language must not exceed 10 characters")
    private String language;
    
    @Size(max = 3, message = "Currency must not exceed 3 characters")
    private String currency;
    
    private Boolean newsletterSubscribed;
    
    private Boolean marketingEmailsEnabled;
    
    private Boolean orderNotificationsEnabled;
    
    private Boolean promotionNotificationsEnabled;
}
