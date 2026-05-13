package com.beej.order.service;

import com.beej.order.dto.*;

import java.util.List;

public interface OrderService {
    
    OrderDTO createOrder(CreateOrderRequestDTO request);
    
    OrderDTO getOrderById(Long id);
    
    OrderDTO getOrderByNumber(String orderNumber);
    
    List<OrderDTO> getUserOrders(Long userId);
    
    List<OrderDTO> getUserOrders(Long userId, int limit);
    
    List<OrderDTO> getOrdersByStatus(String status);
    
    List<OrderDTO> getOrdersByPaymentStatus(String paymentStatus);
    
    List<OrderDTO> getOrdersByPaymentMethod(String paymentMethod);
    
    List<OrderDTO> getOrdersByDateRange(String startDate, String endDate);
    
    List<OrderDTO> searchOrders(String search);
    
    List<OrderDTO> filterOrders(OrderFilterRequestDTO filter);
    
    OrderDTO updateOrder(Long id, UpdateOrderRequestDTO request);
    
    OrderDTO updateOrderStatus(Long id, OrderStatusUpdateRequestDTO request);
    
    OrderDTO cancelOrder(Long id, String reason);
    
    OrderDTO markOrderAsShipped(Long id, String trackingNumber, String carrier);
    
    OrderDTO markOrderAsDelivered(Long id);
    
    void deleteOrder(Long id);
    
    Long countOrdersByStatus(String status);
    
    Long countOrdersByPaymentStatus(String paymentStatus);
    
    java.math.BigDecimal sumTotalByStatusAndDateRange(String status, String startDate, String endDate);
    
    List<String> getDistinctStatuses();
    
    List<String> getDistinctPaymentStatuses();
    
    List<String> getDistinctPaymentMethods();
    
    List<OrderStatusHistoryDTO> getOrderStatusHistory(Long orderId);
    
    List<OrderStatusHistoryDTO> getCustomerVisibleOrderStatusHistory(Long orderId);
}
