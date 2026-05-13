package com.beej.payment.controller;

import com.beej.payment.dto.*;
import com.beej.payment.service.PaymentService;
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
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs for managing payments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Create payment", description = "Create a new payment for an order")
    public ResponseEntity<ApiResponse<PaymentDTO>> createPayment(@Valid @RequestBody CreatePaymentRequestDTO request) {
        PaymentDTO payment = paymentService.createPayment(request);
        return ResponseEntity.ok(ApiResponse.success("Payment created successfully", payment));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID", description = "Retrieve a specific payment by its ID")
    public ResponseEntity<ApiResponse<PaymentDTO>> getPaymentById(
            @Parameter(description = "Payment ID") @PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success("Payment retrieved successfully", payment));
    }

    @GetMapping("/transaction/{transactionId}")
    @Operation(summary = "Get payment by transaction ID", description = "Retrieve a specific payment by its transaction ID")
    public ResponseEntity<ApiResponse<PaymentDTO>> getPaymentByTransactionId(
            @Parameter(description = "Transaction ID") @PathVariable String transactionId) {
        PaymentDTO payment = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(ApiResponse.success("Payment retrieved successfully", payment));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user payments", description = "Retrieve all payments for a specific user")
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> getUserPayments(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<PaymentDTO> payments = paymentService.getUserPayments(userId);
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved successfully", payments));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get order payments", description = "Retrieve all payments for a specific order")
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> getOrderPayments(
            @Parameter(description = "Order ID") @PathVariable Long orderId) {
        List<PaymentDTO> payments = paymentService.getOrderPayments(orderId);
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved successfully", payments));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get payments by status", description = "Retrieve payments with a specific status")
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> getPaymentsByStatus(
            @Parameter(description = "Payment status") @PathVariable String status) {
        List<PaymentDTO> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved successfully", payments));
    }

    @GetMapping("/method/{paymentMethod}")
    @Operation(summary = "Get payments by method", description = "Retrieve payments with a specific payment method")
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> getPaymentsByPaymentMethod(
            @Parameter(description = "Payment method") @PathVariable String paymentMethod) {
        List<PaymentDTO> payments = paymentService.getPaymentsByPaymentMethod(paymentMethod);
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved successfully", payments));
    }

    @GetMapping("/gateway/{paymentGateway}")
    @Operation(summary = "Get payments by gateway", description = "Retrieve payments with a specific payment gateway")
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> getPaymentsByPaymentGateway(
            @Parameter(description = "Payment gateway") @PathVariable String paymentGateway) {
        List<PaymentDTO> payments = paymentService.getPaymentsByPaymentGateway(paymentGateway);
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved successfully", payments));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get payments by date range", description = "Retrieve payments within a date range")
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> getPaymentsByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate) {
        List<PaymentDTO> payments = paymentService.getPaymentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved successfully", payments));
    }

    @GetMapping("/search")
    @Operation(summary = "Search payments", description = "Search payments by order number or customer email")
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> searchPayments(
            @Parameter(description = "Search query") @RequestParam String search) {
        List<PaymentDTO> payments = paymentService.searchPayments(search);
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved successfully", payments));
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "Process payment", description = "Process a payment with gateway response")
    public ResponseEntity<ApiResponse<PaymentDTO>> processPayment(
            @Parameter(description = "Payment ID") @PathVariable Long id,
            @Valid @RequestBody ProcessPaymentRequestDTO request) {
        PaymentDTO payment = paymentService.processPayment(request.toBuilder().paymentId(id).build());
        return ResponseEntity.ok(ApiResponse.success("Payment processed successfully", payment));
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "Complete payment", description = "Mark a payment as completed")
    public ResponseEntity<ApiResponse<PaymentDTO>> completePayment(
            @Parameter(description = "Payment ID") @PathVariable Long id,
            @Parameter(description = "Gateway response") @RequestBody String gatewayResponse) {
        PaymentDTO payment = paymentService.completePayment(id, gatewayResponse);
        return ResponseEntity.ok(ApiResponse.success("Payment completed successfully", payment));
    }

    @PutMapping("/{id}/fail")
    @Operation(summary = "Fail payment", description = "Mark a payment as failed")
    public ResponseEntity<ApiResponse<PaymentDTO>> failPayment(
            @Parameter(description = "Payment ID") @PathVariable Long id,
            @Parameter(description = "Failure reason") @RequestParam String failureReason,
            @Parameter(description = "Gateway response") @RequestBody(required = false) String gatewayResponse) {
        PaymentDTO payment = paymentService.failPayment(id, failureReason, gatewayResponse);
        return ResponseEntity.ok(ApiResponse.success("Payment failed successfully", payment));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel payment", description = "Cancel a payment")
    public ResponseEntity<ApiResponse<PaymentDTO>> cancelPayment(
            @Parameter(description = "Payment ID") @PathVariable Long id,
            @Parameter(description = "Cancellation reason") @RequestParam String reason) {
        PaymentDTO payment = paymentService.cancelPayment(id, reason);
        return ResponseEntity.ok(ApiResponse.success("Payment cancelled successfully", payment));
    }

    @PutMapping("/{id}/expire")
    @Operation(summary = "Expire payment", description = "Mark a payment as expired")
    public ResponseEntity<ApiResponse<PaymentDTO>> expirePayment(
            @Parameter(description = "Payment ID") @PathVariable Long id) {
        PaymentDTO payment = paymentService.expirePayment(id);
        return ResponseEntity.ok(ApiResponse.success("Payment expired successfully", payment));
    }

    @GetMapping("/methods")
    @Operation(summary = "Get distinct payment methods", description = "Get all distinct payment methods")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctPaymentMethods() {
        List<String> methods = paymentService.getDistinctPaymentMethods();
        return ResponseEntity.ok(ApiResponse.success("Payment methods retrieved successfully", methods));
    }

    @GetMapping("/gateways")
    @Operation(summary = "Get distinct payment gateways", description = "Get all distinct payment gateways")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctPaymentGateways() {
        List<String> gateways = paymentService.getDistinctPaymentGateways();
        return ResponseEntity.ok(ApiResponse.success("Payment gateways retrieved successfully", gateways));
    }

    @GetMapping("/stats/status/{status}/count")
    @Operation(summary = "Count payments by status", description = "Get count of payments with a specific status")
    public ResponseEntity<ApiResponse<Long>> countPaymentsByStatus(
            @Parameter(description = "Payment status") @PathVariable String status) {
        Long count = paymentService.countPaymentsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Payment count retrieved successfully", count));
    }

    @GetMapping("/stats/total")
    @Operation(summary = "Sum total by status and date range", description = "Get total amount for payments with specific status in date range")
    public ResponseEntity<ApiResponse<java.math.BigDecimal>> sumAmountByStatusAndDateRange(
            @Parameter(description = "Payment status") @RequestParam String status,
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate) {
        java.math.BigDecimal total = paymentService.sumAmountByStatusAndDateRange(status, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Total amount retrieved successfully", total));
    }

    @DeleteMapping("/cleanup/expired")
    @Operation(summary = "Clean up expired payments", description = "Mark expired payments as failed")
    public ResponseEntity<ApiResponse<Void>> cleanupExpiredPayments() {
        paymentService.cleanupExpiredPayments();
        return ResponseEntity.ok(ApiResponse.success("Expired payments cleaned up successfully"));
    }
}
