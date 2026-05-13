package com.beej.order.mapper;

import com.beej.order.dto.OrderDTO;
import com.beej.order.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;
    private final OrderStatusHistoryMapper statusHistoryMapper;

    public OrderMapper(OrderItemMapper orderItemMapper, OrderStatusHistoryMapper statusHistoryMapper) {
        this.orderItemMapper = orderItemMapper;
        this.statusHistoryMapper = statusHistoryMapper;
    }

    public OrderDTO toOrderDTO(Order order) {
        if (order == null) {
            return null;
        }

        return OrderDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUserId())
                .customerEmail(order.getCustomerEmail())
                .customerFirstName(order.getCustomerFirstName())
                .customerLastName(order.getCustomerLastName())
                .customerPhone(order.getCustomerPhone())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .paymentMethod(order.getPaymentMethod())
                .paymentTransactionId(order.getPaymentTransactionId())
                .subtotal(order.getSubtotal())
                .taxAmount(order.getTaxAmount())
                .shippingAmount(order.getShippingAmount())
                .discountAmount(order.getDiscountAmount())
                .couponCode(order.getCouponCode())
                .couponDiscount(order.getCouponDiscount())
                .total(order.getTotal())
                .currency(order.getCurrency())
                .shippingAddress(order.getShippingAddress())
                .billingAddress(order.getBillingAddress())
                .trackingNumber(order.getTrackingNumber())
                .carrier(order.getCarrier())
                .estimatedDelivery(order.getEstimatedDelivery())
                .shippedAt(order.getShippedAt())
                .deliveredAt(order.getDeliveredAt())
                .cancelledAt(order.getCancelledAt())
                .cancellationReason(order.getCancellationReason())
                .notes(order.getNotes())
                .internalNotes(order.getInternalNotes())
                .items(order.getItems() != null ? 
                    order.getItems().stream()
                        .map(orderItemMapper::toOrderItemDTO)
                        .collect(Collectors.toList()) : null)
                .statusHistory(order.getStatusHistory() != null ? 
                    order.getStatusHistory().stream()
                        .map(statusHistoryMapper::toOrderStatusHistoryDTO)
                        .collect(Collectors.toList()) : null)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public Order toEntity(OrderDTO dto) {
        if (dto == null) {
            return null;
        }

        return Order.builder()
                .id(dto.getId())
                .orderNumber(dto.getOrderNumber())
                .userId(dto.getUserId())
                .customerEmail(dto.getCustomerEmail())
                .customerFirstName(dto.getCustomerFirstName())
                .customerLastName(dto.getCustomerLastName())
                .customerPhone(dto.getCustomerPhone())
                .status(dto.getStatus())
                .paymentStatus(dto.getPaymentStatus())
                .paymentMethod(dto.getPaymentMethod())
                .paymentTransactionId(dto.getPaymentTransactionId())
                .subtotal(dto.getSubtotal())
                .taxAmount(dto.getTaxAmount())
                .shippingAmount(dto.getShippingAmount())
                .discountAmount(dto.getDiscountAmount())
                .couponCode(dto.getCouponCode())
                .couponDiscount(dto.getCouponDiscount())
                .total(dto.getTotal())
                .currency(dto.getCurrency())
                .shippingAddress(dto.getShippingAddress())
                .billingAddress(dto.getBillingAddress())
                .trackingNumber(dto.getTrackingNumber())
                .carrier(dto.getCarrier())
                .estimatedDelivery(dto.getEstimatedDelivery())
                .shippedAt(dto.getShippedAt())
                .deliveredAt(dto.getDeliveredAt())
                .cancelledAt(dto.getCancelledAt())
                .cancellationReason(dto.getCancellationReason())
                .notes(dto.getNotes())
                .internalNotes(dto.getInternalNotes())
                .build();
    }
}
