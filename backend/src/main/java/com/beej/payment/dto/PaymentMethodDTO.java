package com.beej.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO {
    
    private Long id;
    private Long userId;
    private String methodType;
    private String provider;
    private String methodIdentifier;
    private String cardLastFour;
    private String cardBrand;
    private String cardExpiryMonth;
    private String cardExpiryYear;
    private String cardholderName;
    private String gatewayCustomerId;
    private String gatewayPaymentMethodId;
    private Boolean isDefault;
    private Boolean isActive;
    private String billingAddress;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
