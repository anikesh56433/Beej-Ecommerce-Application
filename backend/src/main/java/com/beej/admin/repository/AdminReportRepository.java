package com.beej.admin.repository;

import com.beej.admin.entity.AdminReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminReportRepository extends JpaRepository<AdminReport, Long> {
    
    Page<AdminReport> findByGeneratedByOrderByCreatedAtDesc(Long generatedBy, Pageable pageable);
    
    Page<AdminReport> findByReportTypeOrderByCreatedAtDesc(String reportType, Pageable pageable);
    
    Page<AdminReport> findByStatusOrderByCreatedAtDesc(String status, Pageable pageable);
    
    @Query("SELECT r FROM AdminReport r WHERE r.reportName LIKE %:name% ORDER BY r.createdAt DESC")
    Page<AdminReport> findByReportNameContaining(@Param("name") String name, Pageable pageable);
    
    List<AdminReport> findTop20ByOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(r) FROM AdminReport r WHERE r.status = :status")
    Long countByStatus(@Param("status") String status);
}
