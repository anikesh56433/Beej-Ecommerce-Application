package com.beej.payment.service;

import com.beej.payment.dto.CreateRefundRequestDTO;
import com.beej.payment.dto.RefundDTO;

import java.util.List;

public interface RefundService {
    
    RefundDTO createRefund(CreateRefundRequestDTO request);
    
    RefundDTO getRefundById(Long id);
    
    RefundDTO getRefundByRefundId(String refundId);
    
    List<RefundDTO> getPaymentRefunds(Long paymentId);
    
    List<RefundDTO> getOrderRefunds(Long orderId);
    
    List<RefundDTO> getUserRefunds(Long userId);
    
    List<RefundDTO> getRefundsByStatus(String status);
    
    List<RefundDTO> getRefundsByDateRange(String startDate, String endDate);
    
    RefundDTO processRefund(Long id, String gatewayResponse);
    
    RefundDTO completeRefund(Long id, String gatewayRefundId);
    
    RefundDTO failRefund(Long id, String failureReason, String gatewayResponse);
    
    RefundDTO cancelRefund(Long id, String reason);
    
    List<String> getDistinctStatuses();
    
    Long countRefundsByStatus(String status);
    
    java.math.BigDecimal sumAmountByDateRange(String startDate, String endDate);
    
    void cleanupStaleRefunds();
}
