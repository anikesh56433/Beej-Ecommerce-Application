package com.beej.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    
    private Long id;
    private String orderNumber;
    private Long userId;
    private String customerEmail;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String status;
    private String paymentStatus;
    private String paymentMethod;
    private String paymentTransactionId;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal shippingAmount;
    private BigDecimal discountAmount;
    private String couponCode;
    private BigDecimal couponDiscount;
    private BigDecimal total;
    private String currency;
    private String shippingAddress;
    private String billingAddress;
    private String trackingNumber;
    private String carrier;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime cancelledAt;
    private String cancellationReason;
    private String notes;
    private String internalNotes;
    private List<OrderItemDTO> items;
    private List<OrderStatusHistoryDTO> statusHistory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
