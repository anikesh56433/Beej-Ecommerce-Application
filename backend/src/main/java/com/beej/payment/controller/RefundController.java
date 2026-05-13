package com.beej.payment.controller;

import com.beej.payment.dto.CreateRefundRequestDTO;
import com.beej.payment.dto.RefundDTO;
import com.beej.payment.service.RefundService;
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
@RequestMapping("/payments/refunds")
@RequiredArgsConstructor
@Tag(name = "Refunds", description = "APIs for managing refunds")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    @Operation(summary = "Create refund", description = "Create a new refund for a payment")
    public ResponseEntity<ApiResponse<RefundDTO>> createRefund(@Valid @RequestBody CreateRefundRequestDTO request) {
        RefundDTO refund = refundService.createRefund(request);
        return ResponseEntity.ok(ApiResponse.success("Refund created successfully", refund));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get refund by ID", description = "Retrieve a specific refund by its ID")
    public ResponseEntity<ApiResponse<RefundDTO>> getRefundById(
            @Parameter(description = "Refund ID") @PathVariable Long id) {
        RefundDTO refund = refundService.getRefundById(id);
        return ResponseEntity.ok(ApiResponse.success("Refund retrieved successfully", refund));
    }

    @GetMapping("/refund-id/{refundId}")
    @Operation(summary = "Get refund by refund ID", description = "Retrieve a specific refund by its refund ID")
    public ResponseEntity<ApiResponse<RefundDTO>> getRefundByRefundId(
            @Parameter(description = "Refund ID") @PathVariable String refundId) {
        RefundDTO refund = refundService.getRefundByRefundId(refundId);
        return ResponseEntity.ok(ApiResponse.success("Refund retrieved successfully", refund));
    }

    @GetMapping("/payment/{paymentId}")
    @Operation(summary = "Get payment refunds", description = "Retrieve all refunds for a specific payment")
    public ResponseEntity<ApiResponse<List<RefundDTO>>> getPaymentRefunds(
            @Parameter(description = "Payment ID") @PathVariable Long paymentId) {
        List<RefundDTO> refunds = refundService.getPaymentRefunds(paymentId);
        return ResponseEntity.ok(ApiResponse.success("Refunds retrieved successfully", refunds));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get order refunds", description = "Retrieve all refunds for a specific order")
    public ResponseEntity<ApiResponse<List<RefundDTO>>> getOrderRefunds(
            @Parameter(description = "Order ID") @PathVariable Long orderId) {
        List<RefundDTO> refunds = refundService.getOrderRefunds(orderId);
        return ResponseEntity.ok(ApiResponse.success("Refunds retrieved successfully", refunds));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user refunds", description = "Retrieve all refunds for a specific user")
    public ResponseEntity<ApiResponse<List<RefundDTO>>> getUserRefunds(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<RefundDTO> refunds = refundService.getUserRefunds(userId);
        return ResponseEntity.ok(ApiResponse.success("Refunds retrieved successfully", refunds));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get refunds by status", description = "Retrieve refunds with a specific status")
    public ResponseEntity<ApiResponse<List<RefundDTO>>> getRefundsByStatus(
            @Parameter(description = "Refund status") @PathVariable String status) {
        List<RefundDTO> refunds = refundService.getRefundsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Refunds retrieved successfully", refunds));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get refunds by date range", description = "Retrieve refunds within a date range")
    public ResponseEntity<ApiResponse<List<RefundDTO>>> getRefundsByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate) {
        List<RefundDTO> refunds = refundService.getRefundsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Refunds retrieved successfully", refunds));
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "Process refund", description = "Process a refund with gateway response")
    public ResponseEntity<ApiResponse<RefundDTO>> processRefund(
            @Parameter(description = "Refund ID") @PathVariable Long id,
            @Parameter(description = "Gateway response") @RequestBody String gatewayResponse) {
        RefundDTO refund = refundService.processRefund(id, gatewayResponse);
        return ResponseEntity.ok(ApiResponse.success("Refund processed successfully", refund));
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "Complete refund", description = "Mark a refund as completed")
    public ResponseEntity<ApiResponse<RefundDTO>> completeRefund(
            @Parameter(description = "Refund ID") @PathVariable Long id,
            @Parameter(description = "Gateway refund ID") @RequestBody String gatewayRefundId) {
        RefundDTO refund = refundService.completeRefund(id, gatewayRefundId);
        return ResponseEntity.ok(ApiResponse.success("Refund completed successfully", refund));
    }

    @PutMapping("/{id}/fail")
    @Operation(summary = "Fail refund", description = "Mark a refund as failed")
    public ResponseEntity<ApiResponse<RefundDTO>> failRefund(
            @Parameter(description = "Refund ID") @PathVariable Long id,
            @Parameter(description = "Failure reason") @RequestParam String failureReason,
            @Parameter(description = "Gateway response") @RequestBody(required = false) String gatewayResponse) {
        RefundDTO refund = refundService.failRefund(id, failureReason, gatewayResponse);
        return ResponseEntity.ok(ApiResponse.success("Refund failed successfully", refund));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel refund", description = "Cancel a refund")
    public ResponseEntity<ApiResponse<RefundDTO>> cancelRefund(
            @Parameter(description = "Refund ID") @PathVariable Long id,
            @Parameter(description = "Cancellation reason") @RequestParam String reason) {
        RefundDTO refund = refundService.cancelRefund(id, reason);
        return ResponseEntity.ok(ApiResponse.success("Refund cancelled successfully", refund));
    }

    @GetMapping("/statuses")
    @Operation(summary = "Get distinct statuses", description = "Get all distinct refund statuses")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctStatuses() {
        List<String> statuses = refundService.getDistinctStatuses();
        return ResponseEntity.ok(ApiResponse.success("Refund statuses retrieved successfully", statuses));
    }

    @GetMapping("/stats/status/{status}/count")
    @Operation(summary = "Count refunds by status", description = "Get count of refunds with a specific status")
    public ResponseEntity<ApiResponse<Long>> countRefundsByStatus(
            @Parameter(description = "Refund status") @PathVariable String status) {
        Long count = refundService.countRefundsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Refund count retrieved successfully", count));
    }

    @GetMapping("/stats/total")
    @Operation(summary = "Sum total by date range", description = "Get total refund amount in date range")
    public ResponseEntity<ApiResponse<java.math.BigDecimal>> sumAmountByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate) {
        java.math.BigDecimal total = refundService.sumAmountByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Total refund amount retrieved successfully", total));
    }

    @DeleteMapping("/cleanup/stale")
    @Operation(summary = "Clean up stale refunds", description = "Mark stale refunds as cancelled")
    public ResponseEntity<ApiResponse<Void>> cleanupStaleRefunds() {
        refundService.cleanupStaleRefunds();
        return ResponseEntity.ok(ApiResponse.success("Stale refunds cleaned up successfully"));
    }
}
