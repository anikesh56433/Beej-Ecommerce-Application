package com.beej.admin.repository;

import com.beej.admin.entity.AdminActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdminActivityRepository extends JpaRepository<AdminActivity, Long> {
    
    Page<AdminActivity> findByAdminIdOrderByCreatedAtDesc(Long adminId, Pageable pageable);
    
    Page<AdminActivity> findByActionOrderByCreatedAtDesc(String action, Pageable pageable);
    
    Page<AdminActivity> findByEntityTypeOrderByCreatedAtDesc(String entityType, Pageable pageable);
    
    @Query("SELECT a FROM AdminActivity a WHERE a.adminUsername LIKE %:username% ORDER BY a.createdAt DESC")
    Page<AdminActivity> findByAdminUsernameContaining(@Param("username") String username, Pageable pageable);
    
    @Query("SELECT a FROM AdminActivity a WHERE a.createdAt BETWEEN :startDate AND :endDate ORDER BY a.createdAt DESC")
    Page<AdminActivity> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                       @Param("endDate") LocalDateTime endDate, 
                                       Pageable pageable);
    
    @Query("SELECT a FROM AdminActivity a WHERE a.success = false ORDER BY a.createdAt DESC")
    Page<AdminActivity> findFailedActivities(Pageable pageable);
    
    List<AdminActivity> findTop50ByOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(a) FROM AdminActivity a WHERE a.createdAt > :date")
    Long countActivitiesSince(@Param("date") LocalDateTime date);
    
    @Query("SELECT a.action, COUNT(a) FROM AdminActivity a WHERE a.createdAt BETWEEN :startDate AND :endDate GROUP BY a.action")
    List<Object[]> getActivitySummaryByDateRange(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}
