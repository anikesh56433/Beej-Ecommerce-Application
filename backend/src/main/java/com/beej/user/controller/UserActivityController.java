package com.beej.user.controller;

import com.beej.user.dto.*;
import com.beej.user.service.UserService;
import com.beej.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/activity")
@RequiredArgsConstructor
@Tag(name = "User Activity Management", description = "APIs for managing user activity logs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserActivityController {

    private final UserService userService;

    @PostMapping("/log")
    @Operation(summary = "Log user activity", description = "Log a user activity")
    public ResponseEntity<ApiResponse<Void>> logUserActivity(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Activity type") @RequestParam String activityType,
            @Parameter(description = "Activity description") @RequestParam(required = false) String activityDescription,
            @Parameter(description = "IP Address") @RequestParam(required = false) String ipAddress,
            @Parameter(description = "User Agent") @RequestParam(required = false) String userAgent,
            @Parameter(description = "Device Info") @RequestParam(required = false) String deviceInfo) {
        userService.logUserActivity(userId, activityType, activityDescription, ipAddress, userAgent, deviceInfo);
        return ResponseEntity.ok(ApiResponse.success("Activity logged successfully"));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user activity logs", description = "Retrieve activity logs for a user")
    public ResponseEntity<ApiResponse<List<UserActivityLogDTO>>> getUserActivityLogs(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<UserActivityLogDTO> logs = userService.getUserActivityLogs(userId);
        return ResponseEntity.ok(ApiResponse.success("Activity logs retrieved successfully", logs));
    }

    @GetMapping("/{userId}/type/{activityType}")
    @Operation(summary = "Get user activity logs by type", description = "Retrieve activity logs for a user by activity type")
    public ResponseEntity<ApiResponse<List<UserActivityLogDTO>>> getUserActivityLogsByType(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Activity type") @PathVariable String activityType) {
        List<UserActivityLogDTO> logs = userService.getUserActivityLogsByType(userId, activityType);
        return ResponseEntity.ok(ApiResponse.success("Activity logs retrieved successfully", logs));
    }

    @DeleteMapping("/cleanup")
    @Operation(summary = "Cleanup old activity logs", description = "Delete activity logs older than specified days")
    public ResponseEntity<ApiResponse<Void>> cleanupOldActivityLogs(
            @Parameter(description = "Days to keep") @RequestParam(defaultValue = "90") int days) {
        userService.cleanupOldActivityLogs(days);
        return ResponseEntity.ok(ApiResponse.success("Old activity logs cleaned up successfully"));
    }
}
