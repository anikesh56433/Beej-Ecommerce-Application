package com.beej.notification.repository;

import com.beej.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    Page<Notification> findByUserIdAndIsReadOrderByCreatedAtDesc(Long userId, Boolean isRead, Pageable pageable);
    
    Page<Notification> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, String type, Pageable pageable);
    
    Page<Notification> findByUserIdAndCategoryOrderByCreatedAtDesc(Long userId, String category, Pageable pageable);
    
    Page<Notification> findByUserIdAndPriorityOrderByCreatedAtDesc(Long userId, String priority, Pageable pageable);
    
    @Query("SELECT n FROM Notification n WHERE n.userId = :userId AND n.createdAt BETWEEN :startDate AND :endDate ORDER BY n.createdAt DESC")
    Page<Notification> findByUserIdAndDateRange(@Param("userId") Long userId,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate,
                                               Pageable pageable);
    
    List<Notification> findTop50ByUserIdOrderByCreatedAtDesc(Long userId);
    
    List<Notification> findTop10ByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.isRead = false")
    Long countUnreadNotifications(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.type = :type AND n.isRead = false")
    Long countUnreadNotificationsByType(@Param("userId") Long userId, @Param("type") String type);
    
    @Query("SELECT n FROM Notification n WHERE n.isRead = false AND n.expiresAt < :now")
    List<Notification> findExpiredNotifications(@Param("now") LocalDateTime now);
    
    void deleteByUserId(Long userId);
    
    void deleteByExpiresAtBefore(LocalDateTime date);
}
