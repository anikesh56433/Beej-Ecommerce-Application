package com.beej.notification.controller;

import com.beej.notification.dto.*;
import com.beej.notification.service.NotificationService;
import com.beej.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "APIs for managing user notifications")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Create notification", description = "Create a new notification for a user")
    public ResponseEntity<ApiResponse<NotificationDTO>> createNotification(@Valid @RequestBody CreateNotificationRequestDTO request) {
        NotificationDTO notification = notificationService.createNotification(request);
        return ResponseEntity.ok(ApiResponse.success("Notification created successfully", notification));
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create bulk notifications", description = "Create notifications for multiple users")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> createBulkNotifications(@Valid @RequestBody BulkNotificationRequestDTO request) {
        List<NotificationDTO> notifications = notificationService.createBulkNotifications(request);
        return ResponseEntity.ok(ApiResponse.success("Bulk notifications created successfully", notifications));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID", description = "Retrieve a specific notification by its ID")
    public ResponseEntity<ApiResponse<NotificationDTO>> getNotificationById(
            @Parameter(description = "Notification ID") @PathVariable Long id) {
        NotificationDTO notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(ApiResponse.success("Notification retrieved successfully", notification));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user notifications", description = "Retrieve all notifications for a specific user")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getUserNotifications(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Limit number of results") @RequestParam(required = false) Integer limit) {
        List<NotificationDTO> notifications = limit != null 
                ? notificationService.getUserNotifications(userId, limit)
                : notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved successfully", notifications));
    }

    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Get unread notifications", description = "Retrieve unread notifications for a user")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getUnreadNotifications(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Limit number of results") @RequestParam(required = false) Integer limit) {
        List<NotificationDTO> notifications = limit != null 
                ? notificationService.getUnreadNotifications(userId, limit)
                : notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(ApiResponse.success("Unread notifications retrieved successfully", notifications));
    }

    @GetMapping("/user/{userId}/type/{type}")
    @Operation(summary = "Get notifications by type", description = "Retrieve notifications of a specific type for a user")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getNotificationsByType(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Notification type") @PathVariable String type) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByType(userId, type);
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved successfully", notifications));
    }

    @GetMapping("/user/{userId}/category/{category}")
    @Operation(summary = "Get notifications by category", description = "Retrieve notifications of a specific category for a user")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getNotificationsByCategory(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Notification category") @PathVariable String category) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByCategory(userId, category);
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved successfully", notifications));
    }

    @GetMapping("/user/{userId}/priority/{priority}")
    @Operation(summary = "Get notifications by priority", description = "Retrieve notifications of a specific priority for a user")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getNotificationsByPriority(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Notification priority") @PathVariable String priority) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByPriority(userId, priority);
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved successfully", notifications));
    }

    @GetMapping("/user/{userId}/date-range")
    @Operation(summary = "Get notifications by date range", description = "Retrieve notifications within a date range for a user")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getNotificationsByDateRange(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved successfully", notifications));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark notification as read", description = "Mark a specific notification as read")
    public ResponseEntity<ApiResponse<NotificationDTO>> markAsRead(
            @Parameter(description = "Notification ID") @PathVariable Long id) {
        NotificationDTO notification = notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read", notification));
    }

    @PutMapping("/{id}/unread")
    @Operation(summary = "Mark notification as unread", description = "Mark a specific notification as unread")
    public ResponseEntity<ApiResponse<NotificationDTO>> markAsUnread(
            @Parameter(description = "Notification ID") @PathVariable Long id) {
        NotificationDTO notification = notificationService.markAsUnread(id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as unread", notification));
    }

    @PutMapping("/user/{userId}/read-all")
    @Operation(summary = "Mark all as read", description = "Mark all notifications as read for a user")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> markAllAsRead(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read", notifications));
    }

    @GetMapping("/user/{userId}/unread-count")
    @Operation(summary = "Get unread count", description = "Get count of unread notifications for a user")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        Long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(ApiResponse.success("Unread count retrieved successfully", count));
    }

    @GetMapping("/user/{userId}/unread-count/{type}")
    @Operation(summary = "Get unread count by type", description = "Get count of unread notifications by type for a user")
    public ResponseEntity<ApiResponse<Long>> getUnreadCountByType(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Notification type") @PathVariable String type) {
        Long count = notificationService.getUnreadCountByType(userId, type);
        return ResponseEntity.ok(ApiResponse.success("Unread count retrieved successfully", count));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete notification", description = "Delete a specific notification")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(
            @Parameter(description = "Notification ID") @PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted successfully"));
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete all notifications", description = "Delete all notifications for a user")
    public ResponseEntity<ApiResponse<Void>> deleteAllNotifications(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        notificationService.deleteAllNotifications(userId);
        return ResponseEntity.ok(ApiResponse.success("All notifications deleted successfully"));
    }

    @DeleteMapping("/cleanup/expired")
    @Operation(summary = "Delete expired notifications", description = "Delete all expired notifications")
    public ResponseEntity<ApiResponse<Void>> deleteExpiredNotifications() {
        notificationService.deleteExpiredNotifications();
        return ResponseEntity.ok(ApiResponse.success("Expired notifications deleted successfully"));
    }

    @PostMapping("/template/{templateName}")
    @Operation(summary = "Send from template", description = "Send notification using a template")
    public ResponseEntity<ApiResponse<NotificationDTO>> sendNotificationFromTemplate(
            @Parameter(description = "Template name") @PathVariable String templateName,
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Template variables") @RequestBody(required = false) Map<String, Object> variables) {
        NotificationDTO notification = notificationService.sendNotificationFromTemplate(templateName, userId, variables);
        return ResponseEntity.ok(ApiResponse.success("Notification sent from template successfully", notification));
    }

    @PostMapping("/template/{templateName}/bulk")
    @Operation(summary = "Send bulk from template", description = "Send notifications to multiple users using a template")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> sendBulkNotificationFromTemplate(
            @Parameter(description = "Template name") @PathVariable String templateName,
            @Parameter(description = "User IDs") @RequestBody List<Long> userIds,
            @Parameter(description = "Template variables") @RequestBody(required = false) Map<String, Object> variables) {
        List<NotificationDTO> notifications = notificationService.sendBulkNotificationFromTemplate(templateName, userIds, variables);
        return ResponseEntity.ok(ApiResponse.success("Bulk notifications sent from template successfully", notifications));
    }
}
