package com.beej.payment.mapper;

import com.beej.payment.dto.PaymentMethodDTO;
import com.beej.payment.entity.PaymentMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentMethodMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaymentMethodDTO toPaymentMethodDTO(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            return null;
        }

        Map<String, Object> metadata = parseJsonToMap(paymentMethod.getMetadata());

        return PaymentMethodDTO.builder()
                .id(paymentMethod.getId())
                .userId(paymentMethod.getUserId())
                .methodType(paymentMethod.getMethodType())
                .provider(paymentMethod.getProvider())
                .methodIdentifier(paymentMethod.getMethodIdentifier())
                .cardLastFour(paymentMethod.getCardLastFour())
                .cardBrand(paymentMethod.getCardBrand())
                .cardExpiryMonth(paymentMethod.getCardExpiryMonth())
                .cardExpiryYear(paymentMethod.getCardExpiryYear())
                .cardholderName(paymentMethod.getCardholderName())
                .gatewayCustomerId(paymentMethod.getGatewayCustomerId())
                .gatewayPaymentMethodId(paymentMethod.getGatewayPaymentMethodId())
                .isDefault(paymentMethod.getIsDefault())
                .isActive(paymentMethod.getIsActive())
                .billingAddress(paymentMethod.getBillingAddress())
                .metadata(metadata)
                .createdAt(paymentMethod.getCreatedAt())
                .updatedAt(paymentMethod.getUpdatedAt())
                .build();
    }

    public PaymentMethod toEntity(PaymentMethodDTO dto) {
        if (dto == null) {
            return null;
        }

        String metadataJson = mapToJson(dto.getMetadata());

        return PaymentMethod.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .methodType(dto.getMethodType())
                .provider(dto.getProvider())
                .methodIdentifier(dto.getMethodIdentifier())
                .cardLastFour(dto.getCardLastFour())
                .cardBrand(dto.getCardBrand())
                .cardExpiryMonth(dto.getCardExpiryMonth())
                .cardExpiryYear(dto.getCardExpiryYear())
                .cardholderName(dto.getCardholderName())
                .gatewayCustomerId(dto.getGatewayCustomerId())
                .gatewayPaymentMethodId(dto.getGatewayPaymentMethodId())
                .isDefault(dto.getIsDefault())
                .isActive(dto.getIsActive())
                .billingAddress(dto.getBillingAddress())
                .metadata(metadataJson)
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
