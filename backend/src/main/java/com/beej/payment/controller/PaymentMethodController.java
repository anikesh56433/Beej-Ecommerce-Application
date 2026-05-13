package com.beej.payment.controller;

import com.beej.payment.dto.PaymentMethodDTO;
import com.beej.payment.dto.SavePaymentMethodRequestDTO;
import com.beej.payment.service.PaymentMethodService;
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
@RequestMapping("/payments/methods")
@RequiredArgsConstructor
@Tag(name = "Payment Methods", description = "APIs for managing payment methods")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @PostMapping
    @Operation(summary = "Save payment method", description = "Save a new payment method for a user")
    public ResponseEntity<ApiResponse<PaymentMethodDTO>> savePaymentMethod(@Valid @RequestBody SavePaymentMethodRequestDTO request) {
        PaymentMethodDTO paymentMethod = paymentMethodService.savePaymentMethod(request);
        return ResponseEntity.ok(ApiResponse.success("Payment method saved successfully", paymentMethod));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment method by ID", description = "Retrieve a specific payment method by its ID")
    public ResponseEntity<ApiResponse<PaymentMethodDTO>> getPaymentMethodById(
            @Parameter(description = "Payment method ID") @PathVariable Long id) {
        PaymentMethodDTO paymentMethod = paymentMethodService.getPaymentMethodById(id);
        return ResponseEntity.ok(ApiResponse.success("Payment method retrieved successfully", paymentMethod));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user payment methods", description = "Retrieve all payment methods for a specific user")
    public ResponseEntity<ApiResponse<List<PaymentMethodDTO>>> getUserPaymentMethods(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<PaymentMethodDTO> paymentMethods = paymentMethodService.getUserPaymentMethods(userId);
        return ResponseEntity.ok(ApiResponse.success("Payment methods retrieved successfully", paymentMethods));
    }

    @GetMapping("/user/{userId}/default")
    @Operation(summary = "Get default payment method", description = "Retrieve the default payment method for a user")
    public ResponseEntity<ApiResponse<PaymentMethodDTO>> getDefaultPaymentMethod(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        PaymentMethodDTO paymentMethod = paymentMethodService.getDefaultPaymentMethod(userId);
        return ResponseEntity.ok(ApiResponse.success("Default payment method retrieved successfully", paymentMethod));
    }

    @PutMapping("/user/{userId}/default/{paymentMethodId}")
    @Operation(summary = "Set default payment method", description = "Set a payment method as default for a user")
    public ResponseEntity<ApiResponse<PaymentMethodDTO>> setDefaultPaymentMethod(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Payment method ID") @PathVariable Long paymentMethodId) {
        PaymentMethodDTO paymentMethod = paymentMethodService.setDefaultPaymentMethod(userId, paymentMethodId);
        return ResponseEntity.ok(ApiResponse.success("Default payment method set successfully", paymentMethod));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update payment method", description = "Update an existing payment method")
    public ResponseEntity<ApiResponse<PaymentMethodDTO>> updatePaymentMethod(
            @Parameter(description = "Payment method ID") @PathVariable Long id,
            @Valid @RequestBody SavePaymentMethodRequestDTO request) {
        PaymentMethodDTO paymentMethod = paymentMethodService.updatePaymentMethod(id, request);
        return ResponseEntity.ok(ApiResponse.success("Payment method updated successfully", paymentMethod));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payment method", description = "Delete a specific payment method")
    public ResponseEntity<ApiResponse<Void>> deletePaymentMethod(
            @Parameter(description = "Payment method ID") @PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.ok(ApiResponse.success("Payment method deleted successfully"));
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete all user payment methods", description = "Delete all payment methods for a user")
    public ResponseEntity<ApiResponse<Void>> deleteAllUserPaymentMethods(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        paymentMethodService.deleteAllUserPaymentMethods(userId);
        return ResponseEntity.ok(ApiResponse.success("All payment methods deleted successfully"));
    }

    @GetMapping("/types")
    @Operation(summary = "Get distinct payment method types", description = "Get all distinct active payment method types")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctActiveMethodTypes() {
        List<String> types = paymentMethodService.getDistinctActiveMethodTypes();
        return ResponseEntity.ok(ApiResponse.success("Payment method types retrieved successfully", types));
    }

    @GetMapping("/providers")
    @Operation(summary = "Get distinct providers", description = "Get all distinct active payment providers")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctActiveProviders() {
        List<String> providers = paymentMethodService.getDistinctActiveProviders();
        return ResponseEntity.ok(ApiResponse.success("Payment providers retrieved successfully", providers));
    }
}
