package com.beej.user.controller;

import com.beej.user.dto.*;
import com.beej.user.service.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing user profiles and addresses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @PostMapping("/profile")
    @Operation(summary = "Create user profile", description = "Create a new user profile")
    public ResponseEntity<ApiResponse<UserProfileDTO>> createUserProfile(@Valid @RequestBody CreateUserProfileRequestDTO request) {
        UserProfileDTO profile = userService.createUserProfile(request);
        return ResponseEntity.ok(ApiResponse.success("User profile created successfully", profile));
    }

    @GetMapping("/profile/{id}")
    @Operation(summary = "Get user profile by ID", description = "Retrieve a user profile by its ID")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getUserProfileById(
            @Parameter(description = "Profile ID") @PathVariable Long id) {
        UserProfileDTO profile = userService.getUserProfileById(id);
        return ResponseEntity.ok(ApiResponse.success("User profile retrieved successfully", profile));
    }

    @GetMapping("/{userId}/profile")
    @Operation(summary = "Get user profile by user ID", description = "Retrieve a user profile by user ID")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getUserProfileByUserId(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        UserProfileDTO profile = userService.getUserProfileByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("User profile retrieved successfully", profile));
    }

    @GetMapping("/profile/username/{username}")
    @Operation(summary = "Get user profile by username", description = "Retrieve a user profile by username")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getUserProfileByUsername(
            @Parameter(description = "Username") @PathVariable String username) {
        UserProfileDTO profile = userService.getUserProfileByUsername(username);
        return ResponseEntity.ok(ApiResponse.success("User profile retrieved successfully", profile));
    }

    @GetMapping("/profile/email/{email}")
    @Operation(summary = "Get user profile by email", description = "Retrieve a user profile by email")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getUserProfileByEmail(
            @Parameter(description = "Email") @PathVariable String email) {
        UserProfileDTO profile = userService.getUserProfileByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("User profile retrieved successfully", profile));
    }

    @GetMapping("/profiles")
    @Operation(summary = "Get all user profiles", description = "Retrieve all user profiles")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getAllUserProfiles() {
        List<UserProfileDTO> profiles = userService.getAllUserProfiles();
        return ResponseEntity.ok(ApiResponse.success("User profiles retrieved successfully", profiles));
    }

    @GetMapping("/profiles/search")
    @Operation(summary = "Search user profiles", description = "Search user profiles by name, username, or email")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> searchUserProfiles(
            @Parameter(description = "Search query") @RequestParam String search) {
        List<UserProfileDTO> profiles = userService.searchUserProfiles(search);
        return ResponseEntity.ok(ApiResponse.success("User profiles searched successfully", profiles));
    }

    @GetMapping("/profiles/status/{status}")
    @Operation(summary = "Get profiles by account status", description = "Retrieve user profiles by account status")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getUserProfilesByAccountStatus(
            @Parameter(description = "Account status") @PathVariable String status) {
        List<UserProfileDTO> profiles = userService.getUserProfilesByAccountStatus(status);
        return ResponseEntity.ok(ApiResponse.success("User profiles retrieved successfully", profiles));
    }

    @PutMapping("/profile/{id}")
    @Operation(summary = "Update user profile", description = "Update a user profile by ID")
    public ResponseEntity<ApiResponse<UserProfileDTO>> updateUserProfile(
            @Parameter(description = "Profile ID") @PathVariable Long id,
            @Valid @RequestBody UpdateUserProfileRequestDTO request) {
        UserProfileDTO profile = userService.updateUserProfile(id, request);
        return ResponseEntity.ok(ApiResponse.success("User profile updated successfully", profile));
    }

    @PutMapping("/{userId}/profile")
    @Operation(summary = "Update user profile by user ID", description = "Update a user profile by user ID")
    public ResponseEntity<ApiResponse<UserProfileDTO>> updateUserProfileByUserId(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Valid @RequestBody UpdateUserProfileRequestDTO request) {
        UserProfileDTO profile = userService.updateUserProfileByUserId(userId, request);
        return ResponseEntity.ok(ApiResponse.success("User profile updated successfully", profile));
    }

    @DeleteMapping("/profile/{id}")
    @Operation(summary = "Delete user profile", description = "Delete a user profile by ID")
    public ResponseEntity<ApiResponse<Void>> deleteUserProfile(
            @Parameter(description = "Profile ID") @PathVariable Long id) {
        userService.deleteUserProfile(id);
        return ResponseEntity.ok(ApiResponse.success("User profile deleted successfully"));
    }

    @PutMapping("/{userId}/activate")
    @Operation(summary = "Activate user profile", description = "Activate a user profile")
    public ResponseEntity<ApiResponse<UserProfileDTO>> activateUserProfile(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        UserProfileDTO profile = userService.activateUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.success("User profile activated successfully", profile));
    }

    @PutMapping("/{userId}/deactivate")
    @Operation(summary = "Deactivate user profile", description = "Deactivate a user profile")
    public ResponseEntity<ApiResponse<UserProfileDTO>> deactivateUserProfile(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        UserProfileDTO profile = userService.deactivateUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.success("User profile deactivated successfully", profile));
    }

    @PutMapping("/{userId}/suspend")
    @Operation(summary = "Suspend user profile", description = "Suspend a user profile")
    public ResponseEntity<ApiResponse<UserProfileDTO>> suspendUserProfile(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        UserProfileDTO profile = userService.suspendUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.success("User profile suspended successfully", profile));
    }

    @PutMapping("/{userId}/2fa/enable")
    @Operation(summary = "Enable 2FA", description = "Enable two-factor authentication")
    public ResponseEntity<ApiResponse<UserProfileDTO>> enableTwoFactor(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        UserProfileDTO profile = userService.enableTwoFactor(userId);
        return ResponseEntity.ok(ApiResponse.success("Two-factor authentication enabled successfully", profile));
    }

    @PutMapping("/{userId}/2fa/disable")
    @Operation(summary = "Disable 2FA", description = "Disable two-factor authentication")
    public ResponseEntity<ApiResponse<UserProfileDTO>> disableTwoFactor(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        UserProfileDTO profile = userService.disableTwoFactor(userId);
        return ResponseEntity.ok(ApiResponse.success("Two-factor authentication disabled successfully", profile));
    }

    @PostMapping("/{userId}/login")
    @Operation(summary = "Record login", description = "Record a user login")
    public ResponseEntity<ApiResponse<Void>> recordLogin(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "IP Address") @RequestParam(required = false) String ipAddress) {
        userService.recordLogin(userId, ipAddress);
        return ResponseEntity.ok(ApiResponse.success("Login recorded successfully"));
    }

    @GetMapping("/subscribers/newsletter")
    @Operation(summary = "Get newsletter subscribers", description = "Retrieve all newsletter subscribers")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getNewsletterSubscribers() {
        List<UserProfileDTO> subscribers = userService.getNewsletterSubscribers();
        return ResponseEntity.ok(ApiResponse.success("Newsletter subscribers retrieved successfully", subscribers));
    }

    @GetMapping("/subscribers/marketing")
    @Operation(summary = "Get marketing subscribers", description = "Retrieve all marketing email subscribers")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getMarketingEmailSubscribers() {
        List<UserProfileDTO> subscribers = userService.getMarketingEmailSubscribers();
        return ResponseEntity.ok(ApiResponse.success("Marketing subscribers retrieved successfully", subscribers));
    }

    @GetMapping("/2fa/enabled")
    @Operation(summary = "Get 2FA enabled users", description = "Retrieve all users with 2FA enabled")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getTwoFactorEnabledUsers() {
        List<UserProfileDTO> users = userService.getTwoFactorEnabledUsers();
        return ResponseEntity.ok(ApiResponse.success("2FA enabled users retrieved successfully", users));
    }

    @GetMapping("/inactive")
    @Operation(summary = "Get inactive users", description = "Retrieve users inactive for specified days")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getInactiveUsers(
            @Parameter(description = "Days of inactivity") @RequestParam(defaultValue = "30") int days) {
        List<UserProfileDTO> users = userService.getInactiveUsers(days);
        return ResponseEntity.ok(ApiResponse.success("Inactive users retrieved successfully", users));
    }

    @GetMapping("/active")
    @Operation(summary = "Get recently active users", description = "Retrieve users active in last specified days")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getRecentlyActiveUsers(
            @Parameter(description = "Days of activity") @RequestParam(defaultValue = "30") int days) {
        List<UserProfileDTO> users = userService.getRecentlyActiveUsers(days);
        return ResponseEntity.ok(ApiResponse.success("Recently active users retrieved successfully", users));
    }

    @GetMapping("/stats/status/{status}/count")
    @Operation(summary = "Count by account status", description = "Count users by account status")
    public ResponseEntity<ApiResponse<Long>> countByAccountStatus(
            @Parameter(description = "Account status") @PathVariable String status) {
        Long count = userService.countByAccountStatus(status);
        return ResponseEntity.ok(ApiResponse.success("User count retrieved successfully", count));
    }

    @GetMapping("/stats/newsletter/count")
    @Operation(summary = "Count newsletter subscribers", description = "Count newsletter subscribers")
    public ResponseEntity<ApiResponse<Long>> countNewsletterSubscribers() {
        Long count = userService.countNewsletterSubscribers();
        return ResponseEntity.ok(ApiResponse.success("Newsletter subscriber count retrieved successfully", count));
    }

    @GetMapping("/stats/2fa/count")
    @Operation(summary = "Count 2FA enabled users", description = "Count users with 2FA enabled")
    public ResponseEntity<ApiResponse<Long>> countTwoFactorEnabledUsers() {
        Long count = userService.countTwoFactorEnabledUsers();
        return ResponseEntity.ok(ApiResponse.success("2FA enabled user count retrieved successfully", count));
    }

    @GetMapping("/countries")
    @Operation(summary = "Get distinct countries", description = "Retrieve all distinct countries")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctCountries() {
        List<String> countries = userService.getDistinctCountries();
        return ResponseEntity.ok(ApiResponse.success("Countries retrieved successfully", countries));
    }

    @GetMapping("/languages")
    @Operation(summary = "Get distinct languages", description = "Retrieve all distinct languages")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctLanguages() {
        List<String> languages = userService.getDistinctLanguages();
        return ResponseEntity.ok(ApiResponse.success("Languages retrieved successfully", languages));
    }
}
