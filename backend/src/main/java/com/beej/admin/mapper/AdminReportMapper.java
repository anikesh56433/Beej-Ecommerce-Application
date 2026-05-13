package com.beej.admin.mapper;

import com.beej.admin.dto.AdminReportDTO;
import com.beej.admin.entity.AdminReport;
import org.springframework.stereotype.Component;

@Component
public class AdminReportMapper {

    public AdminReportDTO toAdminReportDTO(AdminReport report) {
        if (report == null) {
            return null;
        }

        return AdminReportDTO.builder()
                .id(report.getId())
                .reportName(report.getReportName())
                .reportType(report.getReportType())
                .description(report.getDescription())
                .generatedBy(report.getGeneratedBy())
                .generatedByUsername(report.getGeneratedByUsername())
                .startDate(report.getStartDate())
                .endDate(report.getEndDate())
                .reportData(report.getReportData())
                .filePath(report.getFilePath())
                .fileName(report.getFileName())
                .fileSize(report.getFileSize())
                .status(report.getStatus())
                .errorMessage(report.getErrorMessage())
                .createdAt(report.getCreatedAt())
                .build();
    }

    public AdminReport toEntity(AdminReportDTO dto) {
        if (dto == null) {
            return null;
        }

        return AdminReport.builder()
                .id(dto.getId())
                .reportName(dto.getReportName())
                .reportType(dto.getReportType())
                .description(dto.getDescription())
                .generatedBy(dto.getGeneratedBy())
                .generatedByUsername(dto.getGeneratedByUsername())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .reportData(dto.getReportData())
                .filePath(dto.getFilePath())
                .fileName(dto.getFileName())
                .fileSize(dto.getFileSize())
                .status(dto.getStatus())
                .errorMessage(dto.getErrorMessage())
                .build();
    }
}
