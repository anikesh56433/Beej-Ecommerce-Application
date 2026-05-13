package com.beej.admin.controller;

import com.beej.admin.dto.AdminActivityDTO;
import com.beej.admin.service.AdminActivityService;
import com.beej.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/activities")
@RequiredArgsConstructor
@Tag(name = "Admin Activity Logging", description = "APIs for managing admin activity logs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminActivityController {

    private final AdminActivityService activityService;

    @PostMapping
    @Operation(summary = "Log admin activity", description = "Log a new admin activity")
    public ResponseEntity<ApiResponse<AdminActivityDTO>> logActivity(@RequestBody AdminActivityDTO activityDTO) {
        AdminActivityDTO activity = activityService.logActivity(activityDTO);
        return ResponseEntity.ok(ApiResponse.success("Activity logged successfully", activity));
    }

    @GetMapping
    @Operation(summary = "Get all activities", description = "Retrieve a paginated list of all admin activities")
    public ResponseEntity<ApiResponse<Page<AdminActivityDTO>>> getActivities(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortOrder) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sortBy));
        
        Page<AdminActivityDTO> activities = activityService.getActivities(pageable);
        return ResponseEntity.ok(ApiResponse.success("Activities retrieved successfully", activities));
    }

    @GetMapping("/admin/{adminId}")
    @Operation(summary = "Get activities by admin", description = "Retrieve activities for a specific admin")
    public ResponseEntity<ApiResponse<Page<AdminActivityDTO>>> getActivitiesByAdmin(
            @Parameter(description = "Admin ID") @PathVariable Long adminId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdminActivityDTO> activities = activityService.getActivitiesByAdmin(adminId, pageable);
        return ResponseEntity.ok(ApiResponse.success("Activities retrieved successfully", activities));
    }

    @GetMapping("/action/{action}")
    @Operation(summary = "Get activities by action", description = "Retrieve activities with a specific action")
    public ResponseEntity<ApiResponse<Page<AdminActivityDTO>>> getActivitiesByAction(
            @Parameter(description = "Action type") @PathVariable String action,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdminActivityDTO> activities = activityService.getActivitiesByAction(action, pageable);
        return ResponseEntity.ok(ApiResponse.success("Activities retrieved successfully", activities));
    }

    @GetMapping("/entity/{entityType}")
    @Operation(summary = "Get activities by entity type", description = "Retrieve activities for a specific entity type")
    public ResponseEntity<ApiResponse<Page<AdminActivityDTO>>> getActivitiesByEntityType(
            @Parameter(description = "Entity type") @PathVariable String entityType,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdminActivityDTO> activities = activityService.getActivitiesByEntityType(entityType, pageable);
        return ResponseEntity.ok(ApiResponse.success("Activities retrieved successfully", activities));
    }

    @GetMapping("/search")
    @Operation(summary = "Search activities", description = "Search activities by username")
    public ResponseEntity<ApiResponse<Page<AdminActivityDTO>>> searchActivities(
            @Parameter(description = "Username to search") @RequestParam String username,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdminActivityDTO> activities = activityService.searchActivities(username, pageable);
        return ResponseEntity.ok(ApiResponse.success("Activities retrieved successfully", activities));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get activities by date range", description = "Retrieve activities within a date range")
    public ResponseEntity<ApiResponse<Page<AdminActivityDTO>>> getActivitiesByDateRange(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdminActivityDTO> activities = activityService.getActivitiesByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(ApiResponse.success("Activities retrieved successfully", activities));
    }

    @GetMapping("/failed")
    @Operation(summary = "Get failed activities", description = "Retrieve all failed activities")
    public ResponseEntity<ApiResponse<Page<AdminActivityDTO>>> getFailedActivities(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdminActivityDTO> activities = activityService.getFailedActivities(pageable);
        return ResponseEntity.ok(ApiResponse.success("Failed activities retrieved successfully", activities));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete activity", description = "Delete a specific activity log")
    public ResponseEntity<ApiResponse<Void>> deleteActivity(
            @Parameter(description = "Activity ID") @PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.ok(ApiResponse.success("Activity deleted successfully"));
    }

    @DeleteMapping("/cleanup")
    @Operation(summary = "Clear old activities", description = "Clear activities before a specific date")
    public ResponseEntity<ApiResponse<Void>> clearOldActivities(
            @Parameter(description = "Clear activities before this date") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beforeDate) {
        activityService.clearOldActivities(beforeDate);
        return ResponseEntity.ok(ApiResponse.success("Old activities cleared successfully"));
    }
}
