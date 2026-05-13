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
public class OrderFilterRequestDTO {
    
    private String status;
    private String paymentStatus;
    private String paymentMethod;
    private String startDate;
    private String endDate;
    private String search;
    private Long userId;
    private String customerEmail;
    private String couponCode;
    private Integer page = 0;
    private Integer limit = 20;
    private String sortBy = "createdAt";
    private String sortOrder = "desc";
}
