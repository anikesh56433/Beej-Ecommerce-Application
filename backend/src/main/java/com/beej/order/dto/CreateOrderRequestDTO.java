package com.beej.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDTO {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Customer email is required")
    private String customerEmail;
    
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    
    @NotNull(message = "Payment method is required")
    private String paymentMethod;
    
    private String paymentTransactionId;
    
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;
    
    private String billingAddress;
    
    private String couponCode;
    
    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<OrderItemRequestDTO> items;
    
    private String notes;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequestDTO {
        @NotNull(message = "Product ID is required")
        private Long productId;
        
        @NotNull(message = "Quantity is required")
        private Integer quantity;
        
        @NotNull(message = "Unit price is required")
        private java.math.BigDecimal unitPrice;
        
        private String productName;
        private String productSku;
        private String productImage;
        private java.math.BigDecimal comparePrice;
        private java.math.BigDecimal discountAmount;
    }
}
