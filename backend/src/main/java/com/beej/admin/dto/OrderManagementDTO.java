package com.beej.admin.dto;

import jakarta.validation.constraints.NotNull;
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
public class OrderManagementDTO {
    
    private Long id;
    private String orderNumber;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    private String customerName;
    private String customerEmail;
    
    private String status;
    private String paymentStatus;
    private String paymentMethod;
    
    @NotNull(message = "Subtotal is required")
    private BigDecimal subtotal;
    
    private BigDecimal taxAmount;
    private BigDecimal shippingAmount;
    private BigDecimal discountAmount;
    
    @NotNull(message = "Total is required")
    private BigDecimal total;
    
    private String currency;
    
    private String shippingAddress;
    private String billingAddress;
    
    private String trackingNumber;
    private String carrier;
    
    private LocalDateTime orderDate;
    private LocalDateTime shippedDate;
    private LocalDateTime deliveredDate;
    private LocalDateTime cancelledDate;
    
    private String notes;
    private String internalNotes;
    
    private List<OrderItemDTO> items;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDTO {
        private Long id;
        private Long productId;
        private String productName;
        private String productSku;
        private String productImage;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderUpdateRequest {
        private String status;
        private String paymentStatus;
        private String trackingNumber;
        private String carrier;
        private String internalNotes;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderFilterRequest {
        private String status;
        private String paymentStatus;
        private String startDate;
        private String endDate;
        private String search;
        private Integer page = 0;
        private Integer limit = 20;
        private String sortBy = "orderDate";
        private String sortOrder = "desc";
    }
}
