package com.beej.payment.repository;

import com.beej.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByTransactionId(String transactionId);
    
    Page<Payment> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    Page<Payment> findByOrderIdOrderByCreatedAtDesc(Long orderId, Pageable pageable);
    
    Page<Payment> findByStatusOrderByCreatedAtDesc(String status, Pageable pageable);
    
    Page<Payment> findByPaymentMethodOrderByCreatedAtDesc(String paymentMethod, Pageable pageable);
    
    Page<Payment> findByPaymentGatewayOrderByCreatedAtDesc(String paymentGateway, Pageable pageable);
    
    @Query("SELECT p FROM Payment p WHERE p.createdAt BETWEEN :startDate AND :endDate ORDER BY p.createdAt DESC")
    Page<Payment> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                  @Param("endDate") LocalDateTime endDate, 
                                  Pageable pageable);
    
    @Query("SELECT p FROM Payment p WHERE p.orderNumber LIKE %:orderNumber% OR p.customerEmail LIKE %:customerEmail% ORDER BY p.createdAt DESC")
    Page<Payment> searchPayments(@Param("orderNumber") String orderNumber, 
                               @Param("customerEmail") String customerEmail, 
                               Pageable pageable);
    
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status")
    Long countByStatus(@Param("status") String status);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = :status AND p.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByStatusAndDateRange(@Param("status") String status,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);
    
    List<Payment> findTop10ByOrderByCreatedAtDesc();
    
    List<Payment> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
    
    @Query("SELECT DISTINCT p.paymentMethod FROM Payment p WHERE p.paymentMethod IS NOT NULL")
    List<String> findDistinctPaymentMethods();
    
    @Query("SELECT DISTINCT p.paymentGateway FROM Payment p WHERE p.paymentGateway IS NOT NULL")
    List<String> findDistinctPaymentGateways();
    
    @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' AND p.expiresAt < :now")
    List<Payment> findExpiredPayments(@Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' AND p.createdAt < :date")
    List<Payment> findStalePayments(@Param("date") LocalDateTime date);
}
