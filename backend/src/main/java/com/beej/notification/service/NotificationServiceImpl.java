package com.beej.notification.service;

import com.beej.notification.dto.*;
import com.beej.notification.entity.Notification;
import com.beej.notification.entity.NotificationTemplate;
import com.beej.notification.mapper.NotificationMapper;
import com.beej.notification.repository.NotificationRepository;
import com.beej.notification.repository.NotificationTemplateRepository;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationTemplateRepository templateRepository;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public NotificationDTO createNotification(CreateNotificationRequestDTO request) {
        log.info("Creating notification for user: {}", request.getUserId());
        
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .category(request.getCategory())
                .priority(request.getPriority())
                .actionUrl(request.getActionUrl())
                .actionText(request.getActionText())
                .iconUrl(request.getIconUrl())
                .imageUrl(request.getImageUrl())
                .metadata(mapToJson(request.getMetadata()))
                .build();
        
        Notification saved = notificationRepository.save(notification);
        log.info("Created notification with ID: {}", saved.getId());
        
        return notificationMapper.toNotificationDTO(saved);
    }

    @Override
    @Transactional
    public List<NotificationDTO> createBulkNotifications(BulkNotificationRequestDTO request) {
        log.info("Creating bulk notifications for {} users", request.getUserIds().size());
        
        List<Notification> notifications = request.getUserIds().stream()
                .map(userId -> Notification.builder()
                        .userId(userId)
                        .title(request.getTitle())
                        .message(request.getMessage())
                        .type(request.getType())
                        .category(request.getCategory())
                        .priority(request.getPriority())
                        .actionUrl(request.getActionUrl())
                        .actionText(request.getActionText())
                        .iconUrl(request.getIconUrl())
                        .imageUrl(request.getImageUrl())
                        .metadata(mapToJson(request.getMetadata()))
                        .build())
                .collect(Collectors.toList());
        
        List<Notification> saved = notificationRepository.saveAll(notifications);
        log.info("Created {} bulk notifications", saved.size());
        
        return saved.stream()
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO getNotificationById(Long id) {
        log.info("Fetching notification with ID: {}", id);
        return notificationRepository.findById(id)
                .map(notificationMapper::toNotificationDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        log.info("Fetching notifications for user: {}", userId);
        return notificationRepository.findTop50ByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId, int limit) {
        log.info("Fetching {} notifications for user: {}", limit, userId);
        return notificationRepository.findTop50ByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .limit(limit)
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        log.info("Fetching unread notifications for user: {}", userId);
        return notificationRepository.findTop10ByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getUnreadNotifications(Long userId, int limit) {
        log.info("Fetching {} unread notifications for user: {}", limit, userId);
        return notificationRepository.findTop10ByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .limit(limit)
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getNotificationsByType(Long userId, String type) {
        log.info("Fetching notifications of type {} for user: {}", type, userId);
        return notificationRepository.findByUserIdAndTypeOrderByCreatedAtDesc(userId, type, null)
                .getContent()
                .stream()
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getNotificationsByCategory(Long userId, String category) {
        log.info("Fetching notifications of category {} for user: {}", category, userId);
        return notificationRepository.findByUserIdAndCategoryOrderByCreatedAtDesc(userId, category, null)
                .getContent()
                .stream()
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getNotificationsByPriority(Long userId, String priority) {
        log.info("Fetching notifications with priority {} for user: {}", priority, userId);
        return notificationRepository.findByUserIdAndPriorityOrderByCreatedAtDesc(userId, priority, null)
                .getContent()
                .stream()
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getNotificationsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching notifications for user {} between {} and {}", userId, startDate, endDate);
        return notificationRepository.findByUserIdAndDateRange(userId, startDate, endDate, null)
                .getContent()
                .stream()
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationDTO markAsRead(Long id) {
        log.info("Marking notification {} as read", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        
        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toNotificationDTO(saved);
    }

    @Override
    @Transactional
    public NotificationDTO markAsUnread(Long id) {
        log.info("Marking notification {} as unread", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        
        notification.setIsRead(false);
        notification.setReadAt(null);
        
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toNotificationDTO(saved);
    }

    @Override
    @Transactional
    public List<NotificationDTO> markAllAsRead(Long userId) {
        log.info("Marking all notifications as read for user: {}", userId);
        List<Notification> notifications = notificationRepository.findTop10ByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        
        notifications.forEach(notification -> {
            notification.setIsRead(true);
            notification.setReadAt(LocalDateTime.now());
        });
        
        List<Notification> saved = notificationRepository.saveAll(notifications);
        return saved.stream()
                .map(notificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        log.info("Deleting notification with ID: {}", id);
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllNotifications(Long userId) {
        log.info("Deleting all notifications for user: {}", userId);
        notificationRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteExpiredNotifications() {
        log.info("Deleting expired notifications");
        List<Notification> expired = notificationRepository.findExpiredNotifications(LocalDateTime.now());
        notificationRepository.deleteAll(expired);
        log.info("Deleted {} expired notifications", expired.size());
    }

    @Override
    public Long getUnreadCount(Long userId) {
        log.info("Getting unread count for user: {}", userId);
        return notificationRepository.countUnreadNotifications(userId);
    }

    @Override
    public Long getUnreadCountByType(Long userId, String type) {
        log.info("Getting unread count for user {} and type {}", userId, type);
        return notificationRepository.countUnreadNotificationsByType(userId, type);
    }

    @Override
    @Transactional
    public NotificationDTO sendNotificationFromTemplate(String templateName, Long userId, Map<String, Object> variables) {
        log.info("Sending notification from template {} to user {}", templateName, userId);
        
        NotificationTemplate template = templateRepository.findByName(templateName)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found: " + templateName));
        
        String title = processTemplate(template.getTitle(), variables);
        String message = processTemplate(template.getMessage(), variables);
        
        CreateNotificationRequestDTO request = CreateNotificationRequestDTO.builder()
                .userId(userId)
                .title(title)
                .message(message)
                .type(template.getType())
                .category(template.getCategory())
                .priority(template.getPriority())
                .build();
        
        return createNotification(request);
    }

    @Override
    @Transactional
    public List<NotificationDTO> sendBulkNotificationFromTemplate(String templateName, List<Long> userIds, Map<String, Object> variables) {
        log.info("Sending bulk notification from template {} to {} users", templateName, userIds.size());
        
        NotificationTemplate template = templateRepository.findByName(templateName)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found: " + templateName));
        
        String title = processTemplate(template.getTitle(), variables);
        String message = processTemplate(template.getMessage(), variables);
        
        BulkNotificationRequestDTO request = BulkNotificationRequestDTO.builder()
                .userIds(userIds)
                .title(title)
                .message(message)
                .type(template.getType())
                .category(template.getCategory())
                .priority(template.getPriority())
                .build();
        
        return createBulkNotifications(request);
    }

    private String mapToJson(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(map);
        } catch (Exception e) {
            return null;
        }
    }

    private String processTemplate(String template, Map<String, Object> variables) {
        if (template == null || variables == null || variables.isEmpty()) {
            return template;
        }
        
        String result = template;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            String value = entry.getValue() != null ? entry.getValue().toString() : "";
            result = result.replace(placeholder, value);
        }
        
        return result;
    }
}
