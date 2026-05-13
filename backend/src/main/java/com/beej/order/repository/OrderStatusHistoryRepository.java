package com.beej.order.repository;

import com.beej.order.entity.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    
    List<OrderStatusHistory> findByOrderIdOrderByCreatedAtDesc(Long orderId);
    
    List<OrderStatusHistory> findByOrderIdAndIsCustomerVisibleOrderByCreatedAtDesc(Long orderId, Boolean isCustomerVisible);
    
    @Query("SELECT osh FROM OrderStatusHistory osh WHERE osh.order.id = :orderId AND osh.status = :status ORDER BY osh.createdAt DESC")
    List<OrderStatusHistory> findByOrderIdAndStatus(@Param("orderId") Long orderId, @Param("status") String status);
    
    @Query("SELECT osh FROM OrderStatusHistory osh WHERE osh.createdAt BETWEEN :startDate AND :endDate ORDER BY osh.createdAt DESC")
    List<OrderStatusHistory> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
    
    void deleteByOrderId(Long orderId);
    
    @Query("SELECT COUNT(osh) FROM OrderStatusHistory osh WHERE osh.status = :status AND osh.createdAt BETWEEN :startDate AND :endDate")
    Long countByStatusAndDateRange(@Param("status") String status,
                                 @Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);
}
