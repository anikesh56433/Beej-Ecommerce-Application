package com.beej.payment.service;

import com.beej.payment.dto.PaymentMethodDTO;
import com.beej.payment.dto.SavePaymentMethodRequestDTO;
import com.beej.payment.entity.PaymentMethod;
import com.beej.payment.mapper.PaymentMethodMapper;
import com.beej.payment.repository.PaymentMethodRepository;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    @Override
    @Transactional
    public PaymentMethodDTO savePaymentMethod(SavePaymentMethodRequestDTO request) {
        log.info("Saving payment method for user: {}", request.getUserId());
        
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .userId(request.getUserId())
                .methodType(request.getMethodType())
                .provider(request.getProvider())
                .methodIdentifier(request.getMethodIdentifier())
                .cardLastFour(request.getCardLastFour())
                .cardBrand(request.getCardBrand())
                .cardExpiryMonth(request.getCardExpiryMonth())
                .cardExpiryYear(request.getCardExpiryYear())
                .cardholderName(request.getCardholderName())
                .gatewayCustomerId(request.getGatewayCustomerId())
                .gatewayPaymentMethodId(request.getGatewayPaymentMethodId())
                .isDefault(request.getIsDefault())
                .isActive(true)
                .billingAddress(request.getBillingAddress())
                .build();
        
        if (request.getIsDefault()) {
            paymentMethodRepository.unsetOtherDefaultMethods(request.getUserId(), null);
        }
        
        PaymentMethod saved = paymentMethodRepository.save(paymentMethod);
        log.info("Saved payment method with ID: {}", saved.getId());
        return paymentMethodMapper.toPaymentMethodDTO(saved);
    }

    @Override
    public PaymentMethodDTO getPaymentMethodById(Long id) {
        log.info("Fetching payment method with ID: {}", id);
        return paymentMethodRepository.findById(id)
                .map(paymentMethodMapper::toPaymentMethodDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + id));
    }

    @Override
    public List<PaymentMethodDTO> getUserPaymentMethods(Long userId) {
        log.info("Fetching payment methods for user: {}", userId);
        return paymentMethodRepository.findByUserId(userId)
                .stream()
                .map(paymentMethodMapper::toPaymentMethodDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentMethodDTO getDefaultPaymentMethod(Long userId) {
        log.info("Fetching default payment method for user: {}", userId);
        return paymentMethodRepository.findByUserIdAndIsDefaultTrue(userId)
                .map(paymentMethodMapper::toPaymentMethodDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public PaymentMethodDTO setDefaultPaymentMethod(Long userId, Long paymentMethodId) {
        log.info("Setting payment method {} as default for user: {}", paymentMethodId, userId);
        
        paymentMethodRepository.unsetOtherDefaultMethods(userId, paymentMethodId);
        
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + paymentMethodId));
        
        paymentMethod.setIsDefault(true);
        PaymentMethod saved = paymentMethodRepository.save(paymentMethod);
        
        log.info("Set payment method {} as default", paymentMethodId);
        return paymentMethodMapper.toPaymentMethodDTO(saved);
    }

    @Override
    @Transactional
    public PaymentMethodDTO updatePaymentMethod(Long id, SavePaymentMethodRequestDTO request) {
        log.info("Updating payment method with ID: {}", id);
        
        PaymentMethod existing = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + id));
        
        existing.setMethodType(request.getMethodType());
        existing.setProvider(request.getProvider());
        existing.setMethodIdentifier(request.getMethodIdentifier());
        existing.setCardLastFour(request.getCardLastFour());
        existing.setCardBrand(request.getCardBrand());
        existing.setCardExpiryMonth(request.getCardExpiryMonth());
        existing.setCardExpiryYear(request.getCardExpiryYear());
        existing.setCardholderName(request.getCardholderName());
        existing.setGatewayCustomerId(request.getGatewayCustomerId());
        existing.setGatewayPaymentMethodId(request.getGatewayPaymentMethodId());
        existing.setBillingAddress(request.getBillingAddress());
        
        if (request.getIsDefault()) {
            paymentMethodRepository.unsetOtherDefaultMethods(existing.getUserId(), id);
            existing.setIsDefault(true);
        }
        
        PaymentMethod saved = paymentMethodRepository.save(existing);
        return paymentMethodMapper.toPaymentMethodDTO(saved);
    }

    @Override
    @Transactional
    public void deletePaymentMethod(Long id) {
        log.info("Deleting payment method with ID: {}", id);
        
        if (!paymentMethodRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment method not found with id: " + id);
        }
        
        paymentMethodRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllUserPaymentMethods(Long userId) {
        log.info("Deleting all payment methods for user: {}", userId);
        paymentMethodRepository.deleteByUserId(userId);
    }

    @Override
    public List<String> getDistinctActiveMethodTypes() {
        return paymentMethodRepository.findDistinctActiveMethodTypes();
    }

    @Override
    public List<String> getDistinctActiveProviders() {
        return paymentMethodRepository.findDistinctActiveProviders();
    }
}
