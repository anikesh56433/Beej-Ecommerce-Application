package com.beej.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateRequestDTO {
    
    @NotBlank(message = "Status is required")
    private String status;
    
    private String comment;
    private Boolean notifyCustomer = true;
    private Boolean isCustomerVisible = true;
}
