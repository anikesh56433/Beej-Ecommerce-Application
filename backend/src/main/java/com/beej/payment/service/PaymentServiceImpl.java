package com.beej.payment.service;

import com.beej.payment.dto.*;
import com.beej.payment.entity.Payment;
import com.beej.payment.mapper.PaymentMapper;
import com.beej.payment.repository.PaymentRepository;
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
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentDTO createPayment(CreatePaymentRequestDTO request) {
        log.info("Creating payment for order: {}", request.getOrderId());
        
        String transactionId = generateTransactionId();
        
        Payment payment = Payment.builder()
                .transactionId(transactionId)
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .paymentMethod(request.getPaymentMethod())
                .paymentGateway(request.getPaymentGateway())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("PENDING")
                .customerEmail(request.getCustomerEmail())
                .customerName(request.getCustomerName())
                .billingAddress(request.getBillingAddress())
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();
        
        Payment saved = paymentRepository.save(payment);
        log.info("Created payment with ID: {} and transaction ID: {}", saved.getId(), saved.getTransactionId());
        return paymentMapper.toPaymentDTO(saved);
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        log.info("Fetching payment with ID: {}", id);
        return paymentRepository.findById(id)
                .map(paymentMapper::toPaymentDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    @Override
    public PaymentDTO getPaymentByTransactionId(String transactionId) {
        log.info("Fetching payment with transaction ID: {}", transactionId);
        return paymentRepository.findByTransactionId(transactionId)
                .map(paymentMapper::toPaymentDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with transaction id: " + transactionId));
    }

    @Override
    public List<PaymentDTO> getUserPayments(Long userId) {
        log.info("Fetching payments for user: {}", userId);
        return paymentRepository.findByUserIdOrderByCreatedAtDesc(userId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(paymentMapper::toPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getOrderPayments(Long orderId) {
        log.info("Fetching payments for order: {}", orderId);
        return paymentRepository.findByOrderIdOrderByCreatedAtDesc(orderId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(paymentMapper::toPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getPaymentsByStatus(String status) {
        log.info("Fetching payments with status: {}", status);
        return paymentRepository.findByStatusOrderByCreatedAtDesc(status, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(paymentMapper::toPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getPaymentsByPaymentMethod(String paymentMethod) {
        log.info("Fetching payments with method: {}", paymentMethod);
        return paymentRepository.findByPaymentMethodOrderByCreatedAtDesc(paymentMethod, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(paymentMapper::toPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getPaymentsByPaymentGateway(String paymentGateway) {
        log.info("Fetching payments with gateway: {}", paymentGateway);
        return paymentRepository.findByPaymentGatewayOrderByCreatedAtDesc(paymentGateway, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(paymentMapper::toPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getPaymentsByDateRange(String startDate, String endDate) {
        log.info("Fetching payments between {} and {}", startDate, endDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        return paymentRepository.findByDateRange(start, end, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(paymentMapper::toPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> searchPayments(String search) {
        log.info("Searching payments with query: {}", search);
        return paymentRepository.searchPayments(search, search, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(paymentMapper::toPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentDTO processPayment(ProcessPaymentRequestDTO request) {
        log.info("Processing payment with ID: {}", request.getPaymentId());
        
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + request.getPaymentId()));
        
        payment.setGatewayResponse(request.getGatewayResponse());
        payment.setIpAddress(request.getIpAddress());
        payment.setUserAgent(request.getUserAgent());
        payment.setProcessedAt(LocalDateTime.now());
        
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toPaymentDTO(saved);
    }

    @Override
    @Transactional
    public PaymentDTO completePayment(Long id, String gatewayResponse) {
        log.info("Completing payment with ID: {}", id);
        
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        payment.setStatus("COMPLETED");
        payment.setGatewayResponse(gatewayResponse);
        payment.setCompletedAt(LocalDateTime.now());
        
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toPaymentDTO(saved);
    }

    @Override
    @Transactional
    public PaymentDTO failPayment(Long id, String failureReason, String gatewayResponse) {
        log.info("Failing payment with ID: {} due to: {}", id, failureReason);
        
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        payment.setStatus("FAILED");
        payment.setFailureReason(failureReason);
        payment.setGatewayResponse(gatewayResponse);
        payment.setFailedAt(LocalDateTime.now());
        
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toPaymentDTO(saved);
    }

    @Override
    @Transactional
    public PaymentDTO cancelPayment(Long id, String reason) {
        log.info("Cancelling payment with ID: {} due to: {}", id, reason);
        
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        payment.setStatus("CANCELLED");
        payment.setFailureReason(reason);
        payment.setFailedAt(LocalDateTime.now());
        
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toPaymentDTO(saved);
    }

    @Override
    @Transactional
    public PaymentDTO expirePayment(Long id) {
        log.info("Expiring payment with ID: {}", id);
        
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        payment.setStatus("EXPIRED");
        payment.setFailedAt(LocalDateTime.now());
        
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toPaymentDTO(saved);
    }

    @Override
    public List<String> getDistinctPaymentMethods() {
        return paymentRepository.findDistinctPaymentMethods();
    }

    @Override
    public List<String> getDistinctPaymentGateways() {
        return paymentRepository.findDistinctPaymentGateways();
    }

    @Override
    public Long countPaymentsByStatus(String status) {
        log.info("Counting payments with status: {}", status);
        return paymentRepository.countByStatus(status);
    }

    @Override
    public BigDecimal sumAmountByStatusAndDateRange(String status, String startDate, String endDate) {
        log.info("Summing amount for payments with status {} between {} and {}", status, startDate, endDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        return paymentRepository.sumAmountByStatusAndDateRange(status, start, end);
    }

    @Override
    @Transactional
    public void cleanupExpiredPayments() {
        log.info("Cleaning up expired payments");
        List<Payment> expired = paymentRepository.findExpiredPayments(LocalDateTime.now());
        expired.forEach(payment -> {
            payment.setStatus("EXPIRED");
            payment.setFailedAt(LocalDateTime.now());
        });
        paymentRepository.saveAll(expired);
        log.info("Cleaned up {} expired payments", expired.size());
    }

    private String generateTransactionId() {
        return "PAY-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
