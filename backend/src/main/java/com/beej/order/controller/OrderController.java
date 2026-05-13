package com.beej.order.controller;

import com.beej.order.dto.*;
import com.beej.order.service.OrderService;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs for managing orders")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create order", description = "Create a new order")
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@Valid @RequestBody CreateOrderRequestDTO request) {
        OrderDTO order = orderService.createOrder(request);
        return ResponseEntity.ok(ApiResponse.success("Order created successfully", order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by its ID")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(
            @Parameter(description = "Order ID") @PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success("Order retrieved successfully", order));
    }

    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "Get order by number", description = "Retrieve a specific order by its order number")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderByNumber(
            @Parameter(description = "Order number") @PathVariable String orderNumber) {
        OrderDTO order = orderService.getOrderByNumber(orderNumber);
        return ResponseEntity.ok(ApiResponse.success("Order retrieved successfully", order));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user orders", description = "Retrieve all orders for a specific user")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getUserOrders(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Limit number of results") @RequestParam(required = false) Integer limit) {
        List<OrderDTO> orders = limit != null 
                ? orderService.getUserOrders(userId, limit)
                : orderService.getUserOrders(userId);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get orders by status", description = "Retrieve orders with a specific status")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByStatus(
            @Parameter(description = "Order status") @PathVariable String status) {
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }

    @GetMapping("/payment-status/{paymentStatus}")
    @Operation(summary = "Get orders by payment status", description = "Retrieve orders with a specific payment status")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByPaymentStatus(
            @Parameter(description = "Payment status") @PathVariable String paymentStatus) {
        List<OrderDTO> orders = orderService.getOrdersByPaymentStatus(paymentStatus);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }

    @GetMapping("/payment-method/{paymentMethod}")
    @Operation(summary = "Get orders by payment method", description = "Retrieve orders with a specific payment method")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByPaymentMethod(
            @Parameter(description = "Payment method") @PathVariable String paymentMethod) {
        List<OrderDTO> orders = orderService.getOrdersByPaymentMethod(paymentMethod);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get orders by date range", description = "Retrieve orders within a date range")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate) {
        List<OrderDTO> orders = orderService.getOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }

    @GetMapping("/search")
    @Operation(summary = "Search orders", description = "Search orders by order number or customer email")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> searchOrders(
            @Parameter(description = "Search query") @RequestParam String search) {
        List<OrderDTO> orders = orderService.searchOrders(search);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter orders", description = "Filter orders with multiple criteria")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> filterOrders(@Valid @RequestBody OrderFilterRequestDTO filter) {
        List<OrderDTO> orders = orderService.filterOrders(filter);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order", description = "Update an existing order")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrder(
            @Parameter(description = "Order ID") @PathVariable Long id,
            @Valid @RequestBody UpdateOrderRequestDTO request) {
        OrderDTO order = orderService.updateOrder(id, request);
        return ResponseEntity.ok(ApiResponse.success("Order updated successfully", order));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status", description = "Update the status of an order")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(
            @Parameter(description = "Order ID") @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateRequestDTO request) {
        OrderDTO order = orderService.updateOrderStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully", order));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel order", description = "Cancel an order with a reason")
    public ResponseEntity<ApiResponse<OrderDTO>> cancelOrder(
            @Parameter(description = "Order ID") @PathVariable Long id,
            @Parameter(description = "Cancellation reason") @RequestParam String reason) {
        OrderDTO order = orderService.cancelOrder(id, reason);
        return ResponseEntity.ok(ApiResponse.success("Order cancelled successfully", order));
    }

    @PutMapping("/{id}/ship")
    @Operation(summary = "Mark order as shipped", description = "Mark an order as shipped with tracking information")
    public ResponseEntity<ApiResponse<OrderDTO>> markOrderAsShipped(
            @Parameter(description = "Order ID") @PathVariable Long id,
            @Parameter(description = "Tracking number") @RequestParam String trackingNumber,
            @Parameter(description = "Carrier") @RequestParam String carrier) {
        OrderDTO order = orderService.markOrderAsShipped(id, trackingNumber, carrier);
        return ResponseEntity.ok(ApiResponse.success("Order marked as shipped successfully", order));
    }

    @PutMapping("/{id}/deliver")
    @Operation(summary = "Mark order as delivered", description = "Mark an order as delivered")
    public ResponseEntity<ApiResponse<OrderDTO>> markOrderAsDelivered(
            @Parameter(description = "Order ID") @PathVariable Long id) {
        OrderDTO order = orderService.markOrderAsDelivered(id);
        return ResponseEntity.ok(ApiResponse.success("Order marked as delivered successfully", order));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order", description = "Delete a specific order")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(
            @Parameter(description = "Order ID") @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Order deleted successfully"));
    }

    @GetMapping("/stats/status/{status}/count")
    @Operation(summary = "Count orders by status", description = "Get count of orders with a specific status")
    public ResponseEntity<ApiResponse<Long>> countOrdersByStatus(
            @Parameter(description = "Order status") @PathVariable String status) {
        Long count = orderService.countOrdersByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Order count retrieved successfully", count));
    }

    @GetMapping("/stats/payment-status/{paymentStatus}/count")
    @Operation(summary = "Count orders by payment status", description = "Get count of orders with a specific payment status")
    public ResponseEntity<ApiResponse<Long>> countOrdersByPaymentStatus(
            @Parameter(description = "Payment status") @PathVariable String paymentStatus) {
        Long count = orderService.countOrdersByPaymentStatus(paymentStatus);
        return ResponseEntity.ok(ApiResponse.success("Order count retrieved successfully", count));
    }

    @GetMapping("/stats/total")
    @Operation(summary = "Sum total by status and date range", description = "Get total revenue for orders with specific status in date range")
    public ResponseEntity<ApiResponse<java.math.BigDecimal>> sumTotalByStatusAndDateRange(
            @Parameter(description = "Order status") @RequestParam String status,
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam String endDate) {
        java.math.BigDecimal total = orderService.sumTotalByStatusAndDateRange(status, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Total sum retrieved successfully", total));
    }

    @GetMapping("/statuses")
    @Operation(summary = "Get distinct statuses", description = "Get all distinct order statuses")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctStatuses() {
        List<String> statuses = orderService.getDistinctStatuses();
        return ResponseEntity.ok(ApiResponse.success("Statuses retrieved successfully", statuses));
    }

    @GetMapping("/payment-statuses")
    @Operation(summary = "Get distinct payment statuses", description = "Get all distinct payment statuses")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctPaymentStatuses() {
        List<String> paymentStatuses = orderService.getDistinctPaymentStatuses();
        return ResponseEntity.ok(ApiResponse.success("Payment statuses retrieved successfully", paymentStatuses));
    }

    @GetMapping("/payment-methods")
    @Operation(summary = "Get distinct payment methods", description = "Get all distinct payment methods")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctPaymentMethods() {
        List<String> paymentMethods = orderService.getDistinctPaymentMethods();
        return ResponseEntity.ok(ApiResponse.success("Payment methods retrieved successfully", paymentMethods));
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Get order status history", description = "Retrieve the status history for a specific order")
    public ResponseEntity<ApiResponse<List<OrderStatusHistoryDTO>>> getOrderStatusHistory(
            @Parameter(description = "Order ID") @PathVariable Long id) {
        List<OrderStatusHistoryDTO> history = orderService.getOrderStatusHistory(id);
        return ResponseEntity.ok(ApiResponse.success("Order status history retrieved successfully", history));
    }

    @GetMapping("/{id}/history/customer")
    @Operation(summary = "Get customer visible order status history", description = "Retrieve customer visible status history for a specific order")
    public ResponseEntity<ApiResponse<List<OrderStatusHistoryDTO>>> getCustomerVisibleOrderStatusHistory(
            @Parameter(description = "Order ID") @PathVariable Long id) {
        List<OrderStatusHistoryDTO> history = orderService.getCustomerVisibleOrderStatusHistory(id);
        return ResponseEntity.ok(ApiResponse.success("Customer visible order status history retrieved successfully", history));
    }
}
