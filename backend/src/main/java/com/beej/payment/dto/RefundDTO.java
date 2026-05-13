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
public class RefundDTO {
    
    private Long id;
    private String refundId;
    private Long paymentId;
    private String paymentTransactionId;
    private Long orderId;
    private String orderNumber;
    private Long userId;
    private BigDecimal amount;
    private String currency;
    private String reason;
    private String status;
    private String gatewayRefundId;
    private Map<String, Object> gatewayResponse;
    private Map<String, Object> gatewayRequest;
    private String failureReason;
    private String processedBy;
    private Long processedById;
    private LocalDateTime processedAt;
    private LocalDateTime completedAt;
    private LocalDateTime failedAt;
    private Boolean customerNotified;
    private LocalDateTime customerNotifiedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
