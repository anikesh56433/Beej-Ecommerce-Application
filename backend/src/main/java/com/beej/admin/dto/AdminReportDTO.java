package com.beej.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminReportDTO {
    
    private Long id;
    private String reportName;
    private String reportType;
    private String description;
    private Long generatedBy;
    private String generatedByUsername;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reportData;
    private String filePath;
    private String fileName;
    private Long fileSize;
    private String status;
    private String errorMessage;
    private LocalDateTime createdAt;
}
