package com.beej.payment.mapper;

import com.beej.payment.dto.PaymentDTO;
import com.beej.payment.entity.Payment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaymentDTO toPaymentDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        Map<String, Object> gatewayResponse = parseJsonToMap(payment.getGatewayResponse());
        Map<String, Object> gatewayRequest = parseJsonToMap(payment.getGatewayRequest());

        return PaymentDTO.builder()
                .id(payment.getId())
                .transactionId(payment.getTransactionId())
                .orderId(payment.getOrderId())
                .orderNumber(payment.getOrderNumber())
                .userId(payment.getUserId())
                .paymentMethod(payment.getPaymentMethod())
                .paymentGateway(payment.getPaymentGateway())
                .gatewayTransactionId(payment.getGatewayTransactionId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .gatewayStatus(payment.getGatewayStatus())
                .failureReason(payment.getFailureReason())
                .gatewayResponse(gatewayResponse)
                .gatewayRequest(gatewayRequest)
                .refundAmount(payment.getRefundAmount())
                .refundReason(payment.getRefundReason())
                .refundDate(payment.getRefundDate())
                .partialRefundCount(payment.getPartialRefundCount())
                .customerEmail(payment.getCustomerEmail())
                .customerName(payment.getCustomerName())
                .billingAddress(payment.getBillingAddress())
                .ipAddress(payment.getIpAddress())
                .userAgent(payment.getUserAgent())
                .processedAt(payment.getProcessedAt())
                .completedAt(payment.getCompletedAt())
                .failedAt(payment.getFailedAt())
                .expiresAt(payment.getExpiresAt())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }

    public Payment toEntity(PaymentDTO dto) {
        if (dto == null) {
            return null;
        }

        String gatewayResponseJson = mapToJson(dto.getGatewayResponse());
        String gatewayRequestJson = mapToJson(dto.getGatewayRequest());

        return Payment.builder()
                .id(dto.getId())
                .transactionId(dto.getTransactionId())
                .orderId(dto.getOrderId())
                .orderNumber(dto.getOrderNumber())
                .userId(dto.getUserId())
                .paymentMethod(dto.getPaymentMethod())
                .paymentGateway(dto.getPaymentGateway())
                .gatewayTransactionId(dto.getGatewayTransactionId())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .status(dto.getStatus())
                .gatewayStatus(dto.getGatewayStatus())
                .failureReason(dto.getFailureReason())
                .gatewayResponse(gatewayResponseJson)
                .gatewayRequest(gatewayRequestJson)
                .refundAmount(dto.getRefundAmount())
                .refundReason(dto.getRefundReason())
                .refundDate(dto.getRefundDate())
                .partialRefundCount(dto.getPartialRefundCount())
                .customerEmail(dto.getCustomerEmail())
                .customerName(dto.getCustomerName())
                .billingAddress(dto.getBillingAddress())
                .ipAddress(dto.getIpAddress())
                .userAgent(dto.getUserAgent())
                .processedAt(dto.getProcessedAt())
                .completedAt(dto.getCompletedAt())
                .failedAt(dto.getFailedAt())
                .expiresAt(dto.getExpiresAt())
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
