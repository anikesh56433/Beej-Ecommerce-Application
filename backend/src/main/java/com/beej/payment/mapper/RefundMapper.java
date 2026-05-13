package com.beej.payment.mapper;

import com.beej.payment.dto.RefundDTO;
import com.beej.payment.entity.Refund;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RefundMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RefundDTO toRefundDTO(Refund refund) {
        if (refund == null) {
            return null;
        }

        Map<String, Object> gatewayResponse = parseJsonToMap(refund.getGatewayResponse());
        Map<String, Object> gatewayRequest = parseJsonToMap(refund.getGatewayRequest());

        return RefundDTO.builder()
                .id(refund.getId())
                .refundId(refund.getRefundId())
                .paymentId(refund.getPaymentId())
                .paymentTransactionId(refund.getPaymentTransactionId())
                .orderId(refund.getOrderId())
                .orderNumber(refund.getOrderNumber())
                .userId(refund.getUserId())
                .amount(refund.getAmount())
                .currency(refund.getCurrency())
                .reason(refund.getReason())
                .status(refund.getStatus())
                .gatewayRefundId(refund.getGatewayRefundId())
                .gatewayResponse(gatewayResponse)
                .gatewayRequest(gatewayRequest)
                .failureReason(refund.getFailureReason())
                .processedBy(refund.getProcessedBy())
                .processedById(refund.getProcessedById())
                .processedAt(refund.getProcessedAt())
                .completedAt(refund.getCompletedAt())
                .failedAt(refund.getFailedAt())
                .customerNotified(refund.getCustomerNotified())
                .customerNotifiedAt(refund.getCustomerNotifiedAt())
                .createdAt(refund.getCreatedAt())
                .updatedAt(refund.getUpdatedAt())
                .build();
    }

    public Refund toEntity(RefundDTO dto) {
        if (dto == null) {
            return null;
        }

        String gatewayResponseJson = mapToJson(dto.getGatewayResponse());
        String gatewayRequestJson = mapToJson(dto.getGatewayRequest());

        return Refund.builder()
                .id(dto.getId())
                .refundId(dto.getRefundId())
                .paymentId(dto.getPaymentId())
                .paymentTransactionId(dto.getPaymentTransactionId())
                .orderId(dto.getOrderId())
                .orderNumber(dto.getOrderNumber())
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .reason(dto.getReason())
                .status(dto.getStatus())
                .gatewayRefundId(dto.getGatewayRefundId())
                .gatewayResponse(gatewayResponseJson)
                .gatewayRequest(gatewayRequestJson)
                .failureReason(dto.getFailureReason())
                .processedBy(dto.getProcessedBy())
                .processedById(dto.getProcessedById())
                .processedAt(dto.getProcessedAt())
                .completedAt(dto.getCompletedAt())
                .failedAt(dto.getFailedAt())
                .customerNotified(dto.getCustomerNotified())
                .customerNotifiedAt(dto.getCustomerNotifiedAt())
                .build();
    }

    private Map<String, Object> parseJsonToMap(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return null;
        }
    }

    private String mapToJson(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            return null;
        }
    }
}
