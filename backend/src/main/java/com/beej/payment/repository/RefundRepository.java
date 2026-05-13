package com.beej.payment.repository;

import com.beej.payment.entity.Refund;
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
public interface RefundRepository extends JpaRepository<Refund, Long> {
    
    Optional<Refund> findByRefundId(String refundId);
    
    Page<Refund> findByPaymentIdOrderByCreatedAtDesc(Long paymentId, Pageable pageable);
    
    Page<Refund> findByOrderIdOrderByCreatedAtDesc(Long orderId, Pageable pageable);
    
    Page<Refund> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    Page<Refund> findByStatusOrderByCreatedAtDesc(String status, Pageable pageable);
    
    @Query("SELECT r FROM Refund r WHERE r.createdAt BETWEEN :startDate AND :endDate ORDER BY r.createdAt DESC")
    Page<Refund> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                @Param("endDate") LocalDateTime endDate, 
                                Pageable pageable);
    
    @Query("SELECT COUNT(r) FROM Refund r WHERE r.status = :status")
    Long countByStatus(@Param("status") String status);
    
    @Query("SELECT SUM(r.amount) FROM Refund r WHERE r.status = 'COMPLETED' AND r.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByDateRange(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);
    
    List<Refund> findTop10ByOrderByCreatedAtDesc();
    
    List<Refund> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
    
    @Query("SELECT DISTINCT r.status FROM Refund r")
    List<String> findDistinctStatuses();
    
    @Query("SELECT r FROM Refund r WHERE r.status = 'PENDING' AND r.createdAt < :date")
    List<Refund> findStaleRefunds(@Param("date") LocalDateTime date);
}
