package com.beej.order.service;

import com.beej.order.dto.*;
import com.beej.order.entity.*;
import com.beej.order.mapper.*;
import com.beej.order.repository.*;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository statusHistoryRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderStatusHistoryMapper statusHistoryMapper;

    @Override
    @Transactional
    public OrderDTO createOrder(CreateOrderRequestDTO request) {
        log.info("Creating order for user: {}", request.getUserId());
        
        String orderNumber = generateOrderNumber();
        
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .userId(request.getUserId())
                .customerEmail(request.getCustomerEmail())
                .customerFirstName(request.getCustomerFirstName())
                .customerLastName(request.getCustomerLastName())
                .customerPhone(request.getCustomerPhone())
                .status("PENDING")
                .paymentStatus("PENDING")
                .paymentMethod(request.getPaymentMethod())
                .paymentTransactionId(request.getPaymentTransactionId())
                .couponCode(request.getCouponCode())
                .shippingAddress(request.getShippingAddress())
                .billingAddress(request.getBillingAddress())
                .notes(request.getNotes())
                .build();
        
        List<OrderItem> items = request.getItems().stream()
                .map(itemRequest -> {
                    OrderItem item = OrderItem.builder()
                            .productId(itemRequest.getProductId())
                            .productName(itemRequest.getProductName())
                            .productSku(itemRequest.getProductSku())
                            .productImage(itemRequest.getProductImage())
                            .quantity(itemRequest.getQuantity())
                            .unitPrice(itemRequest.getUnitPrice())
                            .comparePrice(itemRequest.getComparePrice())
                            .discountAmount(itemRequest.getDiscountAmount())
                            .totalPrice(itemRequest.getUnitPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())))
                            .build();
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());
        
        BigDecimal subtotal = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal taxAmount = subtotal.multiply(BigDecimal.valueOf(0.1));
        BigDecimal shippingAmount = subtotal.compareTo(BigDecimal.valueOf(50)) >= 0 
                ? BigDecimal.ZERO 
                : BigDecimal.valueOf(9.99);
        BigDecimal total = subtotal.add(taxAmount).add(shippingAmount);
        
        order.setSubtotal(subtotal);
        order.setTaxAmount(taxAmount);
        order.setShippingAmount(shippingAmount);
        order.setTotal(total);
        order.setItems(items);
        
        Order saved = orderRepository.save(order);
        
        OrderStatusHistory statusHistory = OrderStatusHistory.builder()
                .order(saved)
                .status("PENDING")
                .comment("Order created")
                .isCustomerVisible(true)
                .notifyCustomer(true)
                .build();
        statusHistoryRepository.save(statusHistory);
        
        log.info("Created order with ID: {} and order number: {}", saved.getId(), saved.getOrderNumber());
        return orderMapper.toOrderDTO(saved);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        return orderRepository.findById(id)
                .map(orderMapper::toOrderDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public OrderDTO getOrderByNumber(String orderNumber) {
        log.info("Fetching order with number: {}", orderNumber);
        return orderRepository.findByOrderNumber(orderNumber)
                .map(orderMapper::toOrderDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with number: " + orderNumber));
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        log.info("Fetching orders for user: {}", userId);
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId, int limit) {
        log.info("Fetching {} orders for user: {}", limit, userId);
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId, 
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(String status) {
        log.info("Fetching orders with status: {}", status);
        return orderRepository.findByStatusOrderByCreatedAtDesc(status, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByPaymentStatus(String paymentStatus) {
        log.info("Fetching orders with payment status: {}", paymentStatus);
        return orderRepository.findByPaymentStatusOrderByCreatedAtDesc(paymentStatus, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByPaymentMethod(String paymentMethod) {
        log.info("Fetching orders with payment method: {}", paymentMethod);
        return orderRepository.findByPaymentMethodOrderByCreatedAtDesc(paymentMethod, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByDateRange(String startDate, String endDate) {
        log.info("Fetching orders between {} and {}", startDate, endDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        return orderRepository.findByDateRange(start, end, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> searchOrders(String search) {
        log.info("Searching orders with query: {}", search);
        return orderRepository.searchOrders(search, search, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> filterOrders(OrderFilterRequestDTO filter) {
        log.info("Filtering orders with criteria: {}", filter);
        
        Sort.Direction direction = filter.getSortOrder().equalsIgnoreCase("desc") 
                ? Sort.Direction.DESC 
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit(), 
                Sort.by(direction, filter.getSortBy()));
        
        Page<Order> orders;
        
        if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            orders = orderRepository.searchOrders(filter.getSearch(), filter.getSearch(), pageable);
        } else if (filter.getUserId() != null) {
            orders = orderRepository.findByUserIdOrderByCreatedAtDesc(filter.getUserId(), pageable);
        } else if (filter.getStatus() != null) {
            orders = orderRepository.findByStatusOrderByCreatedAtDesc(filter.getStatus(), pageable);
        } else if (filter.getPaymentStatus() != null) {
            orders = orderRepository.findByPaymentStatusOrderByCreatedAtDesc(filter.getPaymentStatus(), pageable);
        } else if (filter.getCustomerEmail() != null) {
            orders = orderRepository.findByCustomerEmailContaining(filter.getCustomerEmail(), pageable);
        } else {
            orders = orderRepository.findAll(pageable);
        }
        
        return orders.getContent()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long id, UpdateOrderRequestDTO request) {
        log.info("Updating order with ID: {}", id);
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        if (request.getStatus() != null) {
            updateOrderStatus(order, request.getStatus(), "Order updated", null, true, true);
        }
        
        if (request.getPaymentStatus() != null) {
            order.setPaymentStatus(request.getPaymentStatus());
        }
        
        if (request.getTrackingNumber() != null) {
            order.setTrackingNumber(request.getTrackingNumber());
        }
        
        if (request.getCarrier() != null) {
            order.setCarrier(request.getCarrier());
        }
        
        if (request.getEstimatedDelivery() != null) {
            order.setEstimatedDelivery(request.getEstimatedDelivery());
        }
        
        if (request.getNotes() != null) {
            order.setNotes(request.getNotes());
        }
        
        if (request.getInternalNotes() != null) {
            order.setInternalNotes(request.getInternalNotes());
        }
        
        if (request.getCancellationReason() != null) {
            order.setCancellationReason(request.getCancellationReason());
            order.setCancelledAt(LocalDateTime.now());
        }
        
        Order saved = orderRepository.save(order);
        return orderMapper.toOrderDTO(saved);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(Long id, OrderStatusUpdateRequestDTO request) {
        log.info("Updating status for order {} to {}", id, request.getStatus());
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        updateOrderStatus(order, request.getStatus(), request.getComment(), 
                null, request.getIsCustomerVisible(), request.getNotifyCustomer());
        
        Order saved = orderRepository.save(order);
        return orderMapper.toOrderDTO(saved);
    }

    @Override
    @Transactional
    public OrderDTO cancelOrder(Long id, String reason) {
        log.info("Cancelling order {} with reason: {}", id, reason);
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        updateOrderStatus(order, "CANCELLED", "Order cancelled: " + reason, 
                null, true, true);
        
        order.setCancellationReason(reason);
        order.setCancelledAt(LocalDateTime.now());
        
        Order saved = orderRepository.save(order);
        return orderMapper.toOrderDTO(saved);
    }

    @Override
    @Transactional
    public OrderDTO markOrderAsShipped(Long id, String trackingNumber, String carrier) {
        log.info("Marking order {} as shipped with tracking: {}", id, trackingNumber);
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        updateOrderStatus(order, "SHIPPED", "Order shipped", 
                null, true, true);
        
        order.setTrackingNumber(trackingNumber);
        order.setCarrier(carrier);
        order.setShippedAt(LocalDateTime.now());
        
        Order saved = orderRepository.save(order);
        return orderMapper.toOrderDTO(saved);
    }

    @Override
    @Transactional
    public OrderDTO markOrderAsDelivered(Long id) {
        log.info("Marking order {} as delivered", id);
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        updateOrderStatus(order, "DELIVERED", "Order delivered", 
                null, true, true);
        
        order.setDeliveredAt(LocalDateTime.now());
        
        Order saved = orderRepository.save(order);
        return orderMapper.toOrderDTO(saved);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        log.info("Deleting order with ID: {}", id);
        
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        
        orderRepository.deleteById(id);
    }

    @Override
    public Long countOrdersByStatus(String status) {
        log.info("Counting orders with status: {}", status);
        return orderRepository.countByStatus(status);
    }

    @Override
    public Long countOrdersByPaymentStatus(String paymentStatus) {
        log.info("Counting orders with payment status: {}", paymentStatus);
        return orderRepository.countByPaymentStatus(paymentStatus);
    }

    @Override
    public BigDecimal sumTotalByStatusAndDateRange(String status, String startDate, String endDate) {
        log.info("Summing total for orders with status {} between {} and {}", status, startDate, endDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        return orderRepository.sumTotalByStatusAndDateRange(status, start, end);
    }

    @Override
    public List<String> getDistinctStatuses() {
        return orderRepository.findDistinctStatuses();
    }

    @Override
    public List<String> getDistinctPaymentStatuses() {
        return orderRepository.findDistinctPaymentStatuses();
    }

    @Override
    public List<String> getDistinctPaymentMethods() {
        return orderRepository.findDistinctPaymentMethods();
    }

    @Override
    public List<OrderStatusHistoryDTO> getOrderStatusHistory(Long orderId) {
        log.info("Fetching status history for order: {}", orderId);
        return statusHistoryRepository.findByOrderIdOrderByCreatedAtDesc(orderId)
                .stream()
                .map(statusHistoryMapper::toOrderStatusHistoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderStatusHistoryDTO> getCustomerVisibleOrderStatusHistory(Long orderId) {
        log.info("Fetching customer visible status history for order: {}", orderId);
        return statusHistoryRepository.findByOrderIdAndIsCustomerVisibleOrderByCreatedAtDesc(orderId, true)
                .stream()
                .map(statusHistoryMapper::toOrderStatusHistoryDTO)
                .collect(Collectors.toList());
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void updateOrderStatus(Order order, String newStatus, String comment, 
                                String changedBy, Boolean isCustomerVisible, Boolean notifyCustomer) {
        String previousStatus = order.getStatus();
        order.setStatus(newStatus);
        
        OrderStatusHistory statusHistory = OrderStatusHistory.builder()
                .order(order)
                .status(newStatus)
                .previousStatus(previousStatus)
                .comment(comment)
                .changedBy(changedBy)
                .isCustomerVisible(isCustomerVisible)
                .notifyCustomer(notifyCustomer)
                .build();
        
        statusHistoryRepository.save(statusHistory);
    }
}
