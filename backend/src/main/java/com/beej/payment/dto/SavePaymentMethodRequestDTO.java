package com.beej.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavePaymentMethodRequestDTO {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Method type is required")
    private String methodType;
    
    @NotBlank(message = "Provider is required")
    private String provider;
    
    @NotBlank(message = "Method identifier is required")
    private String methodIdentifier;
    
    private String cardLastFour;
    private String cardBrand;
    private String cardExpiryMonth;
    private String cardExpiryYear;
    private String cardholderName;
    private String gatewayCustomerId;
    private String gatewayPaymentMethodId;
    private Boolean isDefault = false;
    private String billingAddress;
    private Map<String, Object> metadata;
}
