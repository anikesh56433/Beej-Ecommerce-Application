package com.beej.order.repository;

import com.beej.order.entity.Order;
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
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Optional<Order> findByOrderNumber(String orderNumber);
    
    Page<Order> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    Page<Order> findByStatusOrderByCreatedAtDesc(String status, Pageable pageable);
    
    Page<Order> findByPaymentStatusOrderByCreatedAtDesc(String paymentStatus, Pageable pageable);
    
    Page<Order> findByPaymentMethodOrderByCreatedAtDesc(String paymentMethod, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate ORDER BY o.createdAt DESC")
    Page<Order> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                               @Param("endDate") LocalDateTime endDate, 
                               Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.customerEmail LIKE %:email% ORDER BY o.createdAt DESC")
    Page<Order> findByCustomerEmailContaining(@Param("email") String email, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.orderNumber LIKE %:orderNumber% OR o.customerEmail LIKE %:customerEmail% ORDER BY o.createdAt DESC")
    Page<Order> searchOrders(@Param("orderNumber") String orderNumber, 
                           @Param("customerEmail") String customerEmail, 
                           Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.couponCode = :couponCode ORDER BY o.createdAt DESC")
    Page<Order> findByCouponCode(@Param("couponCode") String couponCode, Pageable pageable);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countByStatus(@Param("status") String status);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.paymentStatus = :paymentStatus")
    Long countByPaymentStatus(@Param("paymentStatus") String paymentStatus);
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.status = :status AND o.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalByStatusAndDateRange(@Param("status") String status,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);
    
    List<Order> findTop10ByOrderByCreatedAtDesc();
    
    List<Order> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
    
    @Query("SELECT DISTINCT o.status FROM Order o")
    List<String> findDistinctStatuses();
    
    @Query("SELECT DISTINCT o.paymentStatus FROM Order o")
    List<String> findDistinctPaymentStatuses();
    
    @Query("SELECT DISTINCT o.paymentMethod FROM Order o WHERE o.paymentMethod IS NOT NULL")
    List<String> findDistinctPaymentMethods();
}
