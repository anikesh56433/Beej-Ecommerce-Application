package com.beej.user.repository;

import com.beej.user.entity.UserActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
    
    Page<UserActivityLog> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    List<UserActivityLog> findTop20ByUserIdOrderByCreatedAtDesc(Long userId);
    
    Page<UserActivityLog> findByActivityTypeOrderByCreatedAtDesc(String activityType, Pageable pageable);
    
    @Query("SELECT ual FROM UserActivityLog ual WHERE ual.userId = :userId AND ual.activityType = :activityType ORDER BY ual.createdAt DESC")
    List<UserActivityLog> findByUserIdAndActivityType(@Param("userId") Long userId, @Param("activityType") String activityType);
    
    @Query("SELECT ual FROM UserActivityLog ual WHERE ual.createdAt BETWEEN :startDate AND :endDate ORDER BY ual.createdAt DESC")
    Page<UserActivityLog> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate, 
                                         Pageable pageable);
    
    @Query("SELECT ual FROM UserActivityLog ual WHERE ual.userId = :userId AND ual.createdAt BETWEEN :startDate AND :endDate ORDER BY ual.createdAt DESC")
    List<UserActivityLog> findByUserIdAndDateRange(@Param("userId") Long userId, 
                                                  @Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ual FROM UserActivityLog ual WHERE ual.ipAddress = :ipAddress ORDER BY ual.createdAt DESC")
    List<UserActivityLog> findByIpAddress(@Param("ipAddress") String ipAddress);
    
    @Query("SELECT ual.activityType, COUNT(ual) FROM UserActivityLog ual WHERE ual.userId = :userId GROUP BY ual.activityType")
    List<Object[]> countActivitiesByUserId(@Param("userId") Long userId);
    
    @Query("SELECT ual.activityType, COUNT(ual) FROM UserActivityLog ual GROUP BY ual.activityType")
    List<Object[]> countActivitiesByType();
    
    @Query("SELECT DISTINCT ual.activityType FROM UserActivityLog ual")
    List<String> findDistinctActivityTypes();
    
    Long countByUserId(Long userId);
    
    @Query("SELECT COUNT(ual) FROM UserActivityLog ual WHERE ual.createdAt BETWEEN :startDate AND :endDate")
    Long countByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    void deleteByUserId(Long userId);
    
    @Query("DELETE FROM UserActivityLog ual WHERE ual.createdAt < :date")
    void deleteOldActivityLogs(@Param("date") LocalDateTime date);
}
