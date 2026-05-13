package com.beej.payment.service;

import com.beej.payment.dto.CreateRefundRequestDTO;
import com.beej.payment.dto.RefundDTO;
import com.beej.payment.entity.Refund;
import com.beej.payment.mapper.RefundMapper;
import com.beej.payment.repository.RefundRepository;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final RefundMapper refundMapper;

    @Override
    @Transactional
    public RefundDTO createRefund(CreateRefundRequestDTO request) {
        log.info("Creating refund for payment: {}", request.getPaymentId());
        
        String refundId = generateRefundId();
        
        Refund refund = Refund.builder()
                .refundId(refundId)
                .paymentId(request.getPaymentId())
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .reason(request.getReason())
                .status("PENDING")
                .processedBy(request.getProcessedBy())
                .processedById(request.getProcessedById())
                .customerNotified(request.getNotifyCustomer())
                .build();
        
        Refund saved = refundRepository.save(refund);
        log.info("Created refund with ID: {} and refund ID: {}", saved.getId(), saved.getRefundId());
        return refundMapper.toRefundDTO(saved);
    }

    @Override
    public RefundDTO getRefundById(Long id) {
        log.info("Fetching refund with ID: {}", id);
        return refundRepository.findById(id)
                .map(refundMapper::toRefundDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with id: " + id));
    }

    @Override
    public RefundDTO getRefundByRefundId(String refundId) {
        log.info("Fetching refund with refund ID: {}", refundId);
        return refundRepository.findByRefundId(refundId)
                .map(refundMapper::toRefundDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with refund id: " + refundId));
    }

    @Override
    public List<RefundDTO> getPaymentRefunds(Long paymentId) {
        log.info("Fetching refunds for payment: {}", paymentId);
        return refundRepository.findByPaymentIdOrderByCreatedAtDesc(paymentId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(refundMapper::toRefundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getOrderRefunds(Long orderId) {
        log.info("Fetching refunds for order: {}", orderId);
        return refundRepository.findByOrderIdOrderByCreatedAtDesc(orderId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(refundMapper::toRefundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getUserRefunds(Long userId) {
        log.info("Fetching refunds for user: {}", userId);
        return refundRepository.findByUserIdOrderByCreatedAtDesc(userId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(refundMapper::toRefundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundsByStatus(String status) {
        log.info("Fetching refunds with status: {}", status);
        return refundRepository.findByStatusOrderByCreatedAtDesc(status, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(refundMapper::toRefundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundsByDateRange(String startDate, String endDate) {
        log.info("Fetching refunds between {} and {}", startDate, endDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        return refundRepository.findByDateRange(start, end, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(refundMapper::toRefundDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RefundDTO processRefund(Long id, String gatewayResponse) {
        log.info("Processing refund with ID: {}", id);
        
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with id: " + id));
        
        refund.setGatewayResponse(gatewayResponse);
        refund.setProcessedAt(LocalDateTime.now());
        
        Refund saved = refundRepository.save(refund);
        return refundMapper.toRefundDTO(saved);
    }

    @Override
    @Transactional
    public RefundDTO completeRefund(Long id, String gatewayRefundId) {
        log.info("Completing refund with ID: {} and gateway refund ID: {}", id, gatewayRefundId);
        
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with id: " + id));
        
        refund.setStatus("COMPLETED");
        refund.setGatewayRefundId(gatewayRefundId);
        refund.setCompletedAt(LocalDateTime.now());
        
        Refund saved = refundRepository.save(refund);
        return refundMapper.toRefundDTO(saved);
    }

    @Override
    @Transactional
    public RefundDTO failRefund(Long id, String failureReason, String gatewayResponse) {
        log.info("Failing refund with ID: {} due to: {}", id, failureReason);
        
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with id: " + id));
        
        refund.setStatus("FAILED");
        refund.setFailureReason(failureReason);
        refund.setGatewayResponse(gatewayResponse);
        refund.setFailedAt(LocalDateTime.now());
        
        Refund saved = refundRepository.save(refund);
        return refundMapper.toRefundDTO(saved);
    }

    @Override
    @Transactional
    public RefundDTO cancelRefund(Long id, String reason) {
        log.info("Cancelling refund with ID: {} due to: {}", id, reason);
        
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with id: " + id));
        
        refund.setStatus("CANCELLED");
        refund.setFailureReason(reason);
        refund.setFailedAt(LocalDateTime.now());
        
        Refund saved = refundRepository.save(refund);
        return refundMapper.toRefundDTO(saved);
    }

    @Override
    public List<String> getDistinctStatuses() {
        return refundRepository.findDistinctStatuses();
    }

    @Override
    public Long countRefundsByStatus(String status) {
        log.info("Counting refunds with status: {}", status);
        return refundRepository.countByStatus(status);
    }

    @Override
    public BigDecimal sumAmountByDateRange(String startDate, String endDate) {
        log.info("Summing amount for refunds between {} and {}", startDate, endDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        return refundRepository.sumAmountByDateRange(start, end);
    }

    @Override
    @Transactional
    public void cleanupStaleRefunds() {
        log.info("Cleaning up stale refunds");
        List<Refund> stale = refundRepository.findStaleRefunds(LocalDateTime.now().minusDays(7));
        stale.forEach(refund -> {
            refund.setStatus("CANCELLED");
            refund.setFailureReason("Refund expired due to inactivity");
            refund.setFailedAt(LocalDateTime.now());
        });
        refundRepository.saveAll(stale);
        log.info("Cleaned up {} stale refunds", stale.size());
    }

    private String generateRefundId() {
        return "REF-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
