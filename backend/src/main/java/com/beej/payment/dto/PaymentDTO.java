package com.beej.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    
    private Long id;
    private String transactionId;
    private Long orderId;
    private String orderNumber;
    private Long userId;
    private String paymentMethod;
    private String paymentGateway;
    private String gatewayTransactionId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String gatewayStatus;
    private String failureReason;
    private Map<String, Object> gatewayResponse;
    private Map<String, Object> gatewayRequest;
    private BigDecimal refundAmount;
    private String refundReason;
    private LocalDateTime refundDate;
    private Integer partialRefundCount;
    private String customerEmail;
    private String customerName;
    private String billingAddress;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime processedAt;
    private LocalDateTime completedAt;
    private LocalDateTime failedAt;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
