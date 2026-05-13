package com.beej.admin.dto;

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
public class DashboardStatsDTO {
    
    private Long id;
    private Long totalUsers;
    private Long totalProducts;
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private Long activeUsers;
    private Long pendingOrders;
    private Long processingOrders;
    private Long shippedOrders;
    private Long deliveredOrders;
    private Long cancelledOrders;
    private Long lowStockProducts;
    private Long outOfStockProducts;
    private String recentSignups;
    private String topSellingProducts;
    private Map<String, BigDecimal> revenueByPeriod;
    private Map<String, Long> ordersByStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
