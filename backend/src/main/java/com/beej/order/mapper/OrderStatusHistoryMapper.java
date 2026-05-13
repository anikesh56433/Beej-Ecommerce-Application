package com.beej.order.mapper;

import com.beej.order.dto.OrderStatusHistoryDTO;
import com.beej.order.entity.OrderStatusHistory;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusHistoryMapper {

    public OrderStatusHistoryDTO toOrderStatusHistoryDTO(OrderStatusHistory history) {
        if (history == null) {
            return null;
        }

        return OrderStatusHistoryDTO.builder()
                .id(history.getId())
                .orderId(history.getOrder() != null ? history.getOrder().getId() : null)
                .status(history.getStatus())
                .previousStatus(history.getPreviousStatus())
                .comment(history.getComment())
                .changedBy(history.getChangedBy())
                .changedById(history.getChangedById())
                .isCustomerVisible(history.getIsCustomerVisible())
                .notifyCustomer(history.getNotifyCustomer())
                .createdAt(history.getCreatedAt())
                .build();
    }

    public OrderStatusHistory toEntity(OrderStatusHistoryDTO dto) {
        if (dto == null) {
            return null;
        }

        return OrderStatusHistory.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .previousStatus(dto.getPreviousStatus())
                .comment(dto.getComment())
                .changedBy(dto.getChangedBy())
                .changedById(dto.getChangedById())
                .isCustomerVisible(dto.getIsCustomerVisible())
                .notifyCustomer(dto.getNotifyCustomer())
                .build();
    }
}
