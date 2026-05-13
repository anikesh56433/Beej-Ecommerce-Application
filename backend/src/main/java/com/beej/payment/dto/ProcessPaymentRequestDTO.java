package com.beej.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPaymentRequestDTO {
    
    @NotNull(message = "Payment ID is required")
    private Long paymentId;
    
    @NotBlank(message = "Gateway response is required")
    private String gatewayResponse;
    
    private Map<String, Object> gatewayData;
    
    private String ipAddress;
    private String userAgent;
}
