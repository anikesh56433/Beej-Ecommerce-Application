package com.beej.order.mapper;

import com.beej.order.dto.OrderItemDTO;
import com.beej.order.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemDTO toOrderItemDTO(OrderItem item) {
        if (item == null) {
            return null;
        }

        return OrderItemDTO.builder()
                .id(item.getId())
                .orderId(item.getOrder() != null ? item.getOrder().getId() : null)
                .productId(item.getProductId())
                .productName(item.getProductName())
                .productSku(item.getProductSku())
                .productImage(item.getProductImage())
                .productDescription(item.getProductDescription())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .comparePrice(item.getComparePrice())
                .discountAmount(item.getDiscountAmount())
                .taxAmount(item.getTaxAmount())
                .shippingAmount(item.getShippingAmount())
                .productWeight(item.getProductWeight())
                .productDimensions(item.getProductDimensions())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    public OrderItem toEntity(OrderItemDTO dto) {
        if (dto == null) {
            return null;
        }

        OrderItem item = OrderItem.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .productSku(dto.getProductSku())
                .productImage(dto.getProductImage())
                .productDescription(dto.getProductDescription())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .totalPrice(dto.getTotalPrice())
                .comparePrice(dto.getComparePrice())
                .discountAmount(dto.getDiscountAmount())
                .taxAmount(dto.getTaxAmount())
                .shippingAmount(dto.getShippingAmount())
                .productWeight(dto.getProductWeight())
                .productDimensions(dto.getProductDimensions())
                .build();

        return item;
    }
}
