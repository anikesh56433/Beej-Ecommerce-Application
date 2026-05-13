package com.beej.payment.service;

import com.beej.payment.dto.*;

import java.util.List;

public interface PaymentService {
    
    PaymentDTO createPayment(CreatePaymentRequestDTO request);
    
    PaymentDTO getPaymentById(Long id);
    
    PaymentDTO getPaymentByTransactionId(String transactionId);
    
    List<PaymentDTO> getUserPayments(Long userId);
    
    List<PaymentDTO> getOrderPayments(Long orderId);
    
    List<PaymentDTO> getPaymentsByStatus(String status);
    
    List<PaymentDTO> getPaymentsByPaymentMethod(String paymentMethod);
    
    List<PaymentDTO> getPaymentsByPaymentGateway(String paymentGateway);
    
    List<PaymentDTO> getPaymentsByDateRange(String startDate, String endDate);
    
    List<PaymentDTO> searchPayments(String search);
    
    PaymentDTO processPayment(ProcessPaymentRequestDTO request);
    
    PaymentDTO completePayment(Long id, String gatewayResponse);
    
    PaymentDTO failPayment(Long id, String failureReason, String gatewayResponse);
    
    PaymentDTO cancelPayment(Long id, String reason);
    
    PaymentDTO expirePayment(Long id);
    
    List<String> getDistinctPaymentMethods();
    
    List<String> getDistinctPaymentGateways();
    
    Long countPaymentsByStatus(String status);
    
    java.math.BigDecimal sumAmountByStatusAndDateRange(String status, String startDate, String endDate);
    
    void cleanupExpiredPayments();
}
