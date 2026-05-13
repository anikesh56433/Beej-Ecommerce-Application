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
@RequestMapping("/users/addresses")
@RequiredArgsConstructor
@Tag(name = "User Address Management", description = "APIs for managing user addresses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserAddressController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Add user address", description = "Add a new address for a user")
    public ResponseEntity<ApiResponse<UserAddressDTO>> addUserAddress(@Valid @RequestBody CreateUserAddressRequestDTO request) {
        UserAddressDTO address = userService.addUserAddress(request);
        return ResponseEntity.ok(ApiResponse.success("Address added successfully", address));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get address by ID", description = "Retrieve a specific address by its ID")
    public ResponseEntity<ApiResponse<UserAddressDTO>> getUserAddressById(
            @Parameter(description = "Address ID") @PathVariable Long id) {
        UserAddressDTO address = userService.getUserAddressById(id);
        return ResponseEntity.ok(ApiResponse.success("Address retrieved successfully", address));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user addresses", description = "Retrieve all addresses for a user")
    public ResponseEntity<ApiResponse<List<UserAddressDTO>>> getUserAddresses(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<UserAddressDTO> addresses = userService.getUserAddresses(userId);
        return ResponseEntity.ok(ApiResponse.success("Addresses retrieved successfully", addresses));
    }

    @GetMapping("/user/{userId}/active")
    @Operation(summary = "Get active user addresses", description = "Retrieve all active addresses for a user")
    public ResponseEntity<ApiResponse<List<UserAddressDTO>>> getActiveUserAddresses(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<UserAddressDTO> addresses = userService.getActiveUserAddresses(userId);
        return ResponseEntity.ok(ApiResponse.success("Active addresses retrieved successfully", addresses));
    }

    @GetMapping("/user/{userId}/default")
    @Operation(summary = "Get default user address", description = "Retrieve the default address for a user")
    public ResponseEntity<ApiResponse<UserAddressDTO>> getDefaultUserAddress(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        UserAddressDTO address = userService.getDefaultUserAddress(userId);
        return ResponseEntity.ok(ApiResponse.success("Default address retrieved successfully", address));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user address", description = "Update an existing address")
    public ResponseEntity<ApiResponse<UserAddressDTO>> updateUserAddress(
            @Parameter(description = "Address ID") @PathVariable Long id,
            @Valid @RequestBody UpdateUserAddressRequestDTO request) {
        UserAddressDTO address = userService.updateUserAddress(id, request);
        return ResponseEntity.ok(ApiResponse.success("Address updated successfully", address));
    }

    @PutMapping("/user/{userId}/default/{addressId}")
    @Operation(summary = "Set default address", description = "Set an address as default for a user")
    public ResponseEntity<ApiResponse<UserAddressDTO>> setDefaultAddress(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Address ID") @PathVariable Long addressId) {
        UserAddressDTO address = userService.setDefaultAddress(userId, addressId);
        return ResponseEntity.ok(ApiResponse.success("Default address set successfully", address));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user address", description = "Delete a specific address")
    public ResponseEntity<ApiResponse<Void>> deleteUserAddress(
            @Parameter(description = "Address ID") @PathVariable Long id) {
        userService.deleteUserAddress(id);
        return ResponseEntity.ok(ApiResponse.success("Address deleted successfully"));
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete all user addresses", description = "Delete all addresses for a user")
    public ResponseEntity<ApiResponse<Void>> deleteAllUserAddresses(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        userService.deleteAllUserAddresses(userId);
        return ResponseEntity.ok(ApiResponse.success("All addresses deleted successfully"));
    }

    @GetMapping("/user/{userId}/count")
    @Operation(summary = "Count user addresses", description = "Count active addresses for a user")
    public ResponseEntity<ApiResponse<Long>> countUserAddresses(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        Long count = userService.countUserAddresses(userId);
        return ResponseEntity.ok(ApiResponse.success("Address count retrieved successfully", count));
    }
}
