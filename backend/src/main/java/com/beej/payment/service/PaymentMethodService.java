package com.beej.payment.service;

import com.beej.payment.dto.PaymentMethodDTO;
import com.beej.payment.dto.SavePaymentMethodRequestDTO;

import java.util.List;

public interface PaymentMethodService {
    
    PaymentMethodDTO savePaymentMethod(SavePaymentMethodRequestDTO request);
    
    PaymentMethodDTO getPaymentMethodById(Long id);
    
    List<PaymentMethodDTO> getUserPaymentMethods(Long userId);
    
    PaymentMethodDTO getDefaultPaymentMethod(Long userId);
    
    PaymentMethodDTO setDefaultPaymentMethod(Long userId, Long paymentMethodId);
    
    PaymentMethodDTO updatePaymentMethod(Long id, SavePaymentMethodRequestDTO request);
    
    void deletePaymentMethod(Long id);
    
    void deleteAllUserPaymentMethods(Long userId);
    
    List<String> getDistinctActiveMethodTypes();
    
    List<String> getDistinctActiveProviders();
}
