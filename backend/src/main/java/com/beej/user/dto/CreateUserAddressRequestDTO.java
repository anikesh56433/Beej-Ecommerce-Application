package com.beej.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserAddressRequestDTO {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Address type is required")
    @Size(max = 50, message = "Address type must not exceed 50 characters")
    private String addressType;
    
    private Boolean isDefault = false;
    
    @Size(max = 150, message = "Recipient name must not exceed 150 characters")
    private String recipientName;
    
    @Size(max = 20, message = "Recipient phone must not exceed 20 characters")
    private String recipientPhone;
    
    @NotBlank(message = "Address line 1 is required")
    @Size(max = 255, message = "Address line 1 must not exceed 255 characters")
    private String addressLine1;
    
    @Size(max = 255, message = "Address line 2 must not exceed 255 characters")
    private String addressLine2;
    
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;
    
    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;
    
    @NotBlank(message = "Postal code is required")
    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;
    
    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;
    
    @Size(max = 2, message = "Country code must not exceed 2 characters")
    private String countryCode;
    
    private Double latitude;
    
    private Double longitude;
    
    @Size(max = 500, message = "Delivery instructions must not exceed 500 characters")
    private String deliveryInstructions;
}
