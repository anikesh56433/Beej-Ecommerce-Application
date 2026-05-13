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
public class UpdateOrderRequestDTO {
    
    private String status;
    private String paymentStatus;
    private String trackingNumber;
    private String carrier;
    private LocalDateTime estimatedDelivery;
    private String notes;
    private String internalNotes;
    private String cancellationReason;
}
