package com.beej.admin.service;

import com.beej.admin.dto.AdminActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AdminActivityService {
    
    AdminActivityDTO logActivity(AdminActivityDTO activityDTO);
    
    Page<AdminActivityDTO> getActivities(Pageable pageable);
    
    Page<AdminActivityDTO> getActivitiesByAdmin(Long adminId, Pageable pageable);
    
    Page<AdminActivityDTO> getActivitiesByAction(String action, Pageable pageable);
    
    Page<AdminActivityDTO> getActivitiesByEntityType(String entityType, Pageable pageable);
    
    Page<AdminActivityDTO> searchActivities(String username, Pageable pageable);
    
    Page<AdminActivityDTO> getActivitiesByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    Page<AdminActivityDTO> getFailedActivities(Pageable pageable);
    
    void deleteActivity(Long id);
    
    void clearOldActivities(LocalDateTime beforeDate);
}
