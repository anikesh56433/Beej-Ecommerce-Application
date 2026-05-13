package com.beej.payment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequestDTO {
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    @NotBlank(message = "Payment gateway is required")
    private String paymentGateway;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    private String currency = "USD";
    
    private String customerEmail;
    private String customerName;
    private String billingAddress;
    private String paymentMethodId;
    private Map<String, Object> paymentData;
    private String returnUrl;
    private String cancelUrl;
    private String notificationUrl;
}
