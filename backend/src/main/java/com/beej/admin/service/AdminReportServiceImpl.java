package com.beej.admin.service;

import com.beej.admin.dto.AdminReportDTO;
import com.beej.admin.entity.AdminReport;
import com.beej.admin.mapper.AdminReportMapper;
import com.beej.admin.repository.AdminReportRepository;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminReportServiceImpl implements AdminReportService {

    private final AdminReportRepository reportRepository;
    private final AdminReportMapper reportMapper;

    @Override
    @Transactional
    public AdminReportDTO generateReport(AdminReportDTO reportDTO) {
        log.info("Generating report: {}", reportDTO.getReportName());
        
        AdminReport report = reportMapper.toEntity(reportDTO);
        report.setCreatedAt(LocalDateTime.now());
        report.setStatus("COMPLETED");
        
        AdminReport saved = reportRepository.save(report);
        return reportMapper.toAdminReportDTO(saved);
    }

    @Override
    public Page<AdminReportDTO> getReports(Pageable pageable) {
        log.info("Fetching all reports");
        return reportRepository.findAll(pageable)
                .map(reportMapper::toAdminReportDTO);
    }

    @Override
    public Page<AdminReportDTO> getReportsByType(String reportType, Pageable pageable) {
        log.info("Fetching reports by type: {}", reportType);
        return reportRepository.findByReportTypeOrderByCreatedAtDesc(reportType, pageable)
                .map(reportMapper::toAdminReportDTO);
    }

    @Override
    public Page<AdminReportDTO> getReportsByStatus(String status, Pageable pageable) {
        log.info("Fetching reports by status: {}", status);
        return reportRepository.findByStatusOrderByCreatedAtDesc(status, pageable)
                .map(reportMapper::toAdminReportDTO);
    }

    @Override
    public Page<AdminReportDTO> searchReports(String name, Pageable pageable) {
        log.info("Searching reports by name: {}", name);
        return reportRepository.findByReportNameContaining(name, pageable)
                .map(reportMapper::toAdminReportDTO);
    }

    @Override
    public AdminReportDTO getReportById(Long id) {
        log.info("Fetching report with ID: {}", id);
        return reportRepository.findById(id)
                .map(reportMapper::toAdminReportDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + id));
    }

    @Override
    @Transactional
    public AdminReportDTO updateReport(Long id, AdminReportDTO reportDTO) {
        log.info("Updating report with ID: {}", id);
        
        AdminReport existing = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + id));
        
        existing.setReportName(reportDTO.getReportName());
        existing.setReportType(reportDTO.getReportType());
        existing.setDescription(reportDTO.getDescription());
        existing.setReportData(reportDTO.getReportData());
        existing.setFilePath(reportDTO.getFilePath());
        existing.setFileName(reportDTO.getFileName());
        existing.setFileSize(reportDTO.getFileSize());
        existing.setStatus(reportDTO.getStatus());
        
        AdminReport updated = reportRepository.save(existing);
        return reportMapper.toAdminReportDTO(updated);
    }

    @Override
    @Transactional
    public void deleteReport(Long id) {
        log.info("Deleting report with ID: {}", id);
        reportRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AdminReportDTO generateSalesReport(String startDate, String endDate, Long generatedBy) {
        log.info("Generating sales report from {} to {}", startDate, endDate);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        AdminReport report = AdminReport.builder()
                .reportName("Sales Report - " + startDate + " to " + endDate)
                .reportType("SALES")
                .description("Sales report for the specified date range")
                .generatedBy(generatedBy)
                .startDate(start)
                .endDate(end)
                .status("COMPLETED")
                .build();
        
        AdminReport saved = reportRepository.save(report);
        return reportMapper.toAdminReportDTO(saved);
    }

    @Override
    @Transactional
    public AdminReportDTO generateUserReport(String startDate, String endDate, Long generatedBy) {
        log.info("Generating user report from {} to {}", startDate, endDate);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        AdminReport report = AdminReport.builder()
                .reportName("User Report - " + startDate + " to " + endDate)
                .reportType("USER")
                .description("User report for the specified date range")
                .generatedBy(generatedBy)
                .startDate(start)
                .endDate(end)
                .status("COMPLETED")
                .build();
        
        AdminReport saved = reportRepository.save(report);
        return reportMapper.toAdminReportDTO(saved);
    }

    @Override
    @Transactional
    public AdminReportDTO generateProductReport(String startDate, String endDate, Long generatedBy) {
        log.info("Generating product report from {} to {}", startDate, endDate);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        AdminReport report = AdminReport.builder()
                .reportName("Product Report - " + startDate + " to " + endDate)
                .reportType("PRODUCT")
                .description("Product report for the specified date range")
                .generatedBy(generatedBy)
                .startDate(start)
                .endDate(end)
                .status("COMPLETED")
                .build();
        
        AdminReport saved = reportRepository.save(report);
        return reportMapper.toAdminReportDTO(saved);
    }

    @Override
    @Transactional
    public AdminReportDTO generateInventoryReport(Long generatedBy) {
        log.info("Generating inventory report");
        
        AdminReport report = AdminReport.builder()
                .reportName("Inventory Report")
                .reportType("INVENTORY")
                .description("Current inventory status report")
                .generatedBy(generatedBy)
                .status("COMPLETED")
                .build();
        
        AdminReport saved = reportRepository.save(report);
        return reportMapper.toAdminReportDTO(saved);
    }
}
