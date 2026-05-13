package com.beej.payment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRefundRequestDTO {
    
    @NotNull(message = "Payment ID is required")
    private Long paymentId;
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    private String currency = "USD";
    
    @NotBlank(message = "Reason is required")
    private String reason;
    
    private String processedBy;
    private Long processedById;
    private Boolean notifyCustomer = true;
}
