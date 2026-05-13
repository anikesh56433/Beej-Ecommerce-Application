package com.beej.admin.controller;

import com.beej.admin.dto.DashboardStatsDTO;
import com.beej.admin.service.AdminDashboardService;
import com.beej.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
@Tag(name = "Admin Dashboard", description = "APIs for admin dashboard management")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    @GetMapping("/stats")
    @Operation(summary = "Get dashboard statistics", description = "Retrieve current dashboard statistics")
    public ResponseEntity<ApiResponse<DashboardStatsDTO>> getDashboardStats() {
        DashboardStatsDTO stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success("Dashboard statistics retrieved successfully", stats));
    }

    @PostMapping("/stats/refresh")
    @Operation(summary = "Refresh dashboard statistics", description = "Refresh and recalculate dashboard statistics")
    public ResponseEntity<ApiResponse<DashboardStatsDTO>> refreshDashboardStats() {
        DashboardStatsDTO stats = dashboardService.refreshDashboardStats();
        return ResponseEntity.ok(ApiResponse.success("Dashboard statistics refreshed successfully", stats));
    }

    @GetMapping("/analytics")
    @Operation(summary = "Get analytics data", description = "Retrieve analytics data for a specific period")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAnalyticsData(
            @Parameter(description = "Time period (today, week, month, year)") 
            @RequestParam(defaultValue = "month") String period) {
        Map<String, Object> analytics = dashboardService.getAnalyticsData(period);
        return ResponseEntity.ok(ApiResponse.success("Analytics data retrieved successfully", analytics));
    }

    @GetMapping("/sales")
    @Operation(summary = "Get sales data", description = "Retrieve sales data for a date range")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSalesData(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate) {
        Map<String, Object> salesData = dashboardService.getSalesData(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Sales data retrieved successfully", salesData));
    }

    @GetMapping("/user-growth")
    @Operation(summary = "Get user growth data", description = "Retrieve user growth data for a specific period")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserGrowthData(
            @Parameter(description = "Time period (today, week, month, year)") 
            @RequestParam(defaultValue = "month") String period) {
        Map<String, Object> userGrowth = dashboardService.getUserGrowthData(period);
        return ResponseEntity.ok(ApiResponse.success("User growth data retrieved successfully", userGrowth));
    }

    @GetMapping("/product-performance")
    @Operation(summary = "Get product performance data", description = "Retrieve product performance data for a specific period")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getProductPerformanceData(
            @Parameter(description = "Time period (today, week, month, year)") 
            @RequestParam(defaultValue = "month") String period) {
        Map<String, Object> productPerformance = dashboardService.getProductPerformanceData(period);
        return ResponseEntity.ok(ApiResponse.success("Product performance data retrieved successfully", productPerformance));
    }

    @GetMapping("/order-statistics")
    @Operation(summary = "Get order statistics", description = "Retrieve order statistics for a date range")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getOrderStatistics(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate) {
        Map<String, Object> orderStats = dashboardService.getOrderStatistics(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Order statistics retrieved successfully", orderStats));
    }
}
