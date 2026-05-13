package com.beej.admin.service;

import com.beej.admin.dto.DashboardStatsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AdminDashboardService {
    
    DashboardStatsDTO getDashboardStats();
    
    DashboardStatsDTO refreshDashboardStats();
    
    Map<String, Object> getAnalyticsData(String period);
    
    Map<String, Object> getSalesData(String startDate, String endDate);
    
    Map<String, Object> getUserGrowthData(String period);
    
    Map<String, Object> getProductPerformanceData(String period);
    
    Map<String, Object> getOrderStatistics(String startDate, String endDate);
}
