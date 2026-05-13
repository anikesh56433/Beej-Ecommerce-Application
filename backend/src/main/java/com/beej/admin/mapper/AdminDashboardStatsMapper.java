package com.beej.admin.mapper;

import com.beej.admin.dto.DashboardStatsDTO;
import com.beej.admin.entity.AdminDashboardStats;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AdminDashboardStatsMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public DashboardStatsDTO toDashboardStatsDTO(AdminDashboardStats stats) {
        if (stats == null) {
            return null;
        }

        Map<String, java.math.BigDecimal> revenueByPeriod = parseJsonToMapBigDecimal(stats.getRevenueByPeriod());
        Map<String, Long> ordersByStatus = parseJsonToMapLong(stats.getOrdersByStatus());

        return DashboardStatsDTO.builder()
                .id(stats.getId())
                .totalUsers(stats.getTotalUsers())
                .totalProducts(stats.getTotalProducts())
                .totalOrders(stats.getTotalOrders())
                .totalRevenue(stats.getTotalRevenue())
                .activeUsers(stats.getActiveUsers())
                .pendingOrders(stats.getPendingOrders())
                .processingOrders(stats.getProcessingOrders())
                .shippedOrders(stats.getShippedOrders())
                .deliveredOrders(stats.getDeliveredOrders())
                .cancelledOrders(stats.getCancelledOrders())
                .lowStockProducts(stats.getLowStockProducts())
                .outOfStockProducts(stats.getOutOfStockProducts())
                .recentSignups(stats.getRecentSignups())
                .topSellingProducts(stats.getTopSellingProducts())
                .revenueByPeriod(revenueByPeriod)
                .ordersByStatus(ordersByStatus)
                .createdAt(stats.getCreatedAt())
                .updatedAt(stats.getUpdatedAt())
                .build();
    }

    public AdminDashboardStats toEntity(DashboardStatsDTO dto) {
        if (dto == null) {
            return null;
        }

        String revenueByPeriodJson = mapToJson(dto.getRevenueByPeriod());
        String ordersByStatusJson = mapToJson(dto.getOrdersByStatus());

        return AdminDashboardStats.builder()
                .id(dto.getId())
                .totalUsers(dto.getTotalUsers())
                .totalProducts(dto.getTotalProducts())
                .totalOrders(dto.getTotalOrders())
                .totalRevenue(dto.getTotalRevenue())
                .activeUsers(dto.getActiveUsers())
                .pendingOrders(dto.getPendingOrders())
                .processingOrders(dto.getProcessingOrders())
                .shippedOrders(dto.getShippedOrders())
                .deliveredOrders(dto.getDeliveredOrders())
                .cancelledOrders(dto.getCancelledOrders())
                .lowStockProducts(dto.getLowStockProducts())
                .outOfStockProducts(dto.getOutOfStockProducts())
                .recentSignups(dto.getRecentSignups())
                .topSellingProducts(dto.getTopSellingProducts())
                .revenueByPeriod(revenueByPeriodJson)
                .ordersByStatus(ordersByStatusJson)
                .build();
    }

    private Map<String, Object> parseJsonToMap(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, java.math.BigDecimal> parseJsonToMapBigDecimal(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, java.math.BigDecimal>>() {});
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, Long> parseJsonToMapLong(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Long>>() {});
        } catch (Exception e) {
            return null;
        }
    }

    private String mapToJson(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            return null;
        }
    }
}
