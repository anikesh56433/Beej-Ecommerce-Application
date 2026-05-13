package com.beej.notification.service;

import com.beej.notification.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    
    NotificationDTO createNotification(CreateNotificationRequestDTO request);
    
    List<NotificationDTO> createBulkNotifications(BulkNotificationRequestDTO request);
    
    NotificationDTO getNotificationById(Long id);
    
    List<NotificationDTO> getUserNotifications(Long userId);
    
    List<NotificationDTO> getUserNotifications(Long userId, int limit);
    
    List<NotificationDTO> getUnreadNotifications(Long userId);
    
    List<NotificationDTO> getUnreadNotifications(Long userId, int limit);
    
    List<NotificationDTO> getNotificationsByType(Long userId, String type);
    
    List<NotificationDTO> getNotificationsByCategory(Long userId, String category);
    
    List<NotificationDTO> getNotificationsByPriority(Long userId, String priority);
    
    List<NotificationDTO> getNotificationsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    NotificationDTO markAsRead(Long id);
    
    NotificationDTO markAsUnread(Long id);
    
    List<NotificationDTO> markAllAsRead(Long userId);
    
    void deleteNotification(Long id);
    
    void deleteAllNotifications(Long userId);
    
    void deleteExpiredNotifications();
    
    Long getUnreadCount(Long userId);
    
    Long getUnreadCountByType(Long userId, String type);
    
    NotificationDTO sendNotificationFromTemplate(String templateName, Long userId, java.util.Map<String, Object> variables);
    
    List<NotificationDTO> sendBulkNotificationFromTemplate(String templateName, List<Long> userIds, java.util.Map<String, Object> variables);
}
