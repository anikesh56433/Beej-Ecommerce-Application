package com.beej.notification.controller;

import com.beej.notification.dto.NotificationTemplateDTO;
import com.beej.notification.service.NotificationTemplateService;
import com.beej.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications/templates")
@RequiredArgsConstructor
@Tag(name = "Notification Templates", description = "APIs for managing notification templates")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationTemplateController {

    private final NotificationTemplateService templateService;

    @PostMapping
    @Operation(summary = "Create template", description = "Create a new notification template")
    public ResponseEntity<ApiResponse<NotificationTemplateDTO>> createTemplate(@Valid @RequestBody NotificationTemplateDTO templateDTO) {
        NotificationTemplateDTO template = templateService.createTemplate(templateDTO);
        return ResponseEntity.ok(ApiResponse.success("Template created successfully", template));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get template by ID", description = "Retrieve a specific template by its ID")
    public ResponseEntity<ApiResponse<NotificationTemplateDTO>> getTemplateById(
            @Parameter(description = "Template ID") @PathVariable Long id) {
        NotificationTemplateDTO template = templateService.getTemplateById(id);
        return ResponseEntity.ok(ApiResponse.success("Template retrieved successfully", template));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get template by name", description = "Retrieve a specific template by its name")
    public ResponseEntity<ApiResponse<NotificationTemplateDTO>> getTemplateByName(
            @Parameter(description = "Template name") @PathVariable String name) {
        NotificationTemplateDTO template = templateService.getTemplateByName(name);
        return ResponseEntity.ok(ApiResponse.success("Template retrieved successfully", template));
    }

    @GetMapping
    @Operation(summary = "Get all templates", description = "Retrieve all notification templates")
    public ResponseEntity<ApiResponse<List<NotificationTemplateDTO>>> getAllTemplates() {
        List<NotificationTemplateDTO> templates = templateService.getAllTemplates();
        return ResponseEntity.ok(ApiResponse.success("Templates retrieved successfully", templates));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get templates by type", description = "Retrieve templates of a specific type")
    public ResponseEntity<ApiResponse<List<NotificationTemplateDTO>>> getTemplatesByType(
            @Parameter(description = "Template type") @PathVariable String type) {
        List<NotificationTemplateDTO> templates = templateService.getTemplatesByType(type);
        return ResponseEntity.ok(ApiResponse.success("Templates retrieved successfully", templates));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get templates by category", description = "Retrieve templates of a specific category")
    public ResponseEntity<ApiResponse<List<NotificationTemplateDTO>>> getTemplatesByCategory(
            @Parameter(description = "Template category") @PathVariable String category) {
        List<NotificationTemplateDTO> templates = templateService.getTemplatesByCategory(category);
        return ResponseEntity.ok(ApiResponse.success("Templates retrieved successfully", templates));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active templates", description = "Retrieve all active notification templates")
    public ResponseEntity<ApiResponse<List<NotificationTemplateDTO>>> getActiveTemplates() {
        List<NotificationTemplateDTO> templates = templateService.getActiveTemplates();
        return ResponseEntity.ok(ApiResponse.success("Active templates retrieved successfully", templates));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update template", description = "Update an existing notification template")
    public ResponseEntity<ApiResponse<NotificationTemplateDTO>> updateTemplate(
            @Parameter(description = "Template ID") @PathVariable Long id,
            @Valid @RequestBody NotificationTemplateDTO templateDTO) {
        NotificationTemplateDTO template = templateService.updateTemplate(id, templateDTO);
        return ResponseEntity.ok(ApiResponse.success("Template updated successfully", template));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete template", description = "Delete a specific notification template")
    public ResponseEntity<ApiResponse<Void>> deleteTemplate(
            @Parameter(description = "Template ID") @PathVariable Long id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.ok(ApiResponse.success("Template deleted successfully"));
    }

    @GetMapping("/categories")
    @Operation(summary = "Get active categories", description = "Retrieve all active notification categories")
    public ResponseEntity<ApiResponse<List<String>>> getActiveCategories() {
        List<String> categories = templateService.getActiveCategories();
        return ResponseEntity.ok(ApiResponse.success("Active categories retrieved successfully", categories));
    }

    @GetMapping("/types")
    @Operation(summary = "Get active types", description = "Retrieve all active notification types")
    public ResponseEntity<ApiResponse<List<String>>> getActiveTypes() {
        List<String> types = templateService.getActiveTypes();
        return ResponseEntity.ok(ApiResponse.success("Active types retrieved successfully", types));
    }
}
