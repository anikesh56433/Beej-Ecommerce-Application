package com.beej.admin.controller;

import com.beej.admin.dto.AdminReportDTO;
import com.beej.admin.service.AdminReportService;
import com.beej.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
@Tag(name = "Admin Reports", description = "APIs for managing admin reports")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminReportController {

    private final AdminReportService reportService;

    @PostMapping
    @Operation(summary = "Generate report", description = "Generate a new admin report")
    public ResponseEntity<ApiResponse<AdminReportDTO>> generateReport(@Valid @RequestBody AdminReportDTO reportDTO) {
        AdminReportDTO report = reportService.generateReport(reportDTO);
        return ResponseEntity.ok(ApiResponse.success("Report generated successfully", report));
    }

    @GetMapping
    @Operation(summary = "Get all reports", description = "Retrieve a paginated list of all reports")
    public ResponseEntity<ApiResponse<Page<AdminReportDTO>>> getReports(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortOrder) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sortBy));
        
        Page<AdminReportDTO> reports = reportService.getReports(pageable);
        return ResponseEntity.ok(ApiResponse.success("Reports retrieved successfully", reports));
    }

    @GetMapping("/type/{reportType}")
    @Operation(summary = "Get reports by type", description = "Retrieve reports of a specific type")
    public ResponseEntity<ApiResponse<Page<AdminReportDTO>>> getReportsByType(
            @Parameter(description = "Report type") @PathVariable String reportType,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdminReportDTO> reports = reportService.getReportsByType(reportType, pageable);
        return ResponseEntity.ok(ApiResponse.success("Reports retrieved successfully", reports));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get reports by status", description = "Retrieve reports with a specific status")
    public ResponseEntity<ApiResponse<Page<AdminReportDTO>>> getReportsByStatus(
            @Parameter(description = "Report status") @PathVariable String status,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdminReportDTO> reports = reportService.getReportsByStatus(status, pageable);
        return ResponseEntity.ok(ApiResponse.success("Reports retrieved successfully", reports));
    }

    @GetMapping("/search")
    @Operation(summary = "Search reports", description = "Search reports by name")
    public ResponseEntity<ApiResponse<Page<AdminReportDTO>>> searchReports(
            @Parameter(description = "Report name to search") @RequestParam String name,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdminReportDTO> reports = reportService.searchReports(name, pageable);
        return ResponseEntity.ok(ApiResponse.success("Reports retrieved successfully", reports));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get report by ID", description = "Retrieve a specific report by its ID")
    public ResponseEntity<ApiResponse<AdminReportDTO>> getReportById(
            @Parameter(description = "Report ID") @PathVariable Long id) {
        AdminReportDTO report = reportService.getReportById(id);
        return ResponseEntity.ok(ApiResponse.success("Report retrieved successfully", report));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update report", description = "Update an existing report")
    public ResponseEntity<ApiResponse<AdminReportDTO>> updateReport(
            @Parameter(description = "Report ID") @PathVariable Long id,
            @Valid @RequestBody AdminReportDTO reportDTO) {
        AdminReportDTO report = reportService.updateReport(id, reportDTO);
        return ResponseEntity.ok(ApiResponse.success("Report updated successfully", report));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete report", description = "Delete a specific report")
    public ResponseEntity<ApiResponse<Void>> deleteReport(
            @Parameter(description = "Report ID") @PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok(ApiResponse.success("Report deleted successfully"));
    }

    @PostMapping("/sales")
    @Operation(summary = "Generate sales report", description = "Generate a sales report for a date range")
    public ResponseEntity<ApiResponse<AdminReportDTO>> generateSalesReport(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate,
            @Parameter(description = "Generated by user ID") @RequestParam Long generatedBy) {
        AdminReportDTO report = reportService.generateSalesReport(startDate, endDate, generatedBy);
        return ResponseEntity.ok(ApiResponse.success("Sales report generated successfully", report));
    }

    @PostMapping("/users")
    @Operation(summary = "Generate user report", description = "Generate a user report for a date range")
    public ResponseEntity<ApiResponse<AdminReportDTO>> generateUserReport(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate,
            @Parameter(description = "Generated by user ID") @RequestParam Long generatedBy) {
        AdminReportDTO report = reportService.generateUserReport(startDate, endDate, generatedBy);
        return ResponseEntity.ok(ApiResponse.success("User report generated successfully", report));
    }

    @PostMapping("/products")
    @Operation(summary = "Generate product report", description = "Generate a product report for a date range")
    public ResponseEntity<ApiResponse<AdminReportDTO>> generateProductReport(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate,
            @Parameter(description = "Generated by user ID") @RequestParam Long generatedBy) {
        AdminReportDTO report = reportService.generateProductReport(startDate, endDate, generatedBy);
        return ResponseEntity.ok(ApiResponse.success("Product report generated successfully", report));
    }

    @PostMapping("/inventory")
    @Operation(summary = "Generate inventory report", description = "Generate an inventory report")
    public ResponseEntity<ApiResponse<AdminReportDTO>> generateInventoryReport(
            @Parameter(description = "Generated by user ID") @RequestParam Long generatedBy) {
        AdminReportDTO report = reportService.generateInventoryReport(generatedBy);
        return ResponseEntity.ok(ApiResponse.success("Inventory report generated successfully", report));
    }
}
