package com.beej.admin.service;

import com.beej.admin.dto.AdminReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminReportService {
    
    AdminReportDTO generateReport(AdminReportDTO reportDTO);
    
    Page<AdminReportDTO> getReports(Pageable pageable);
    
    Page<AdminReportDTO> getReportsByType(String reportType, Pageable pageable);
    
    Page<AdminReportDTO> getReportsByStatus(String status, Pageable pageable);
    
    Page<AdminReportDTO> searchReports(String name, Pageable pageable);
    
    AdminReportDTO getReportById(Long id);
    
    AdminReportDTO updateReport(Long id, AdminReportDTO reportDTO);
    
    void deleteReport(Long id);
    
    AdminReportDTO generateSalesReport(String startDate, String endDate, Long generatedBy);
    
    AdminReportDTO generateUserReport(String startDate, String endDate, Long generatedBy);
    
    AdminReportDTO generateProductReport(String startDate, String endDate, Long generatedBy);
    
    AdminReportDTO generateInventoryReport(Long generatedBy);
}
