package com.beej.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusHistoryDTO {
    
    private Long id;
    private Long orderId;
    private String status;
    private String previousStatus;
    private String comment;
    private String changedBy;
    private Long changedById;
    private Boolean isCustomerVisible;
    private Boolean notifyCustomer;
    private LocalDateTime createdAt;
}
