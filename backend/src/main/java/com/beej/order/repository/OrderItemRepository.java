package com.beej.order.repository;

import com.beej.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    List<OrderItem> findByOrderId(Long orderId);
    
    List<OrderItem> findByProductId(Long productId);
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId AND oi.productId = :productId")
    OrderItem findByOrderIdAndProductId(@Param("orderId") Long orderId, @Param("productId") Long productId);
    
    void deleteByOrderId(Long orderId);
    
    @Query("SELECT COUNT(oi) FROM OrderItem oi WHERE oi.productId = :productId")
    Long countByProductId(@Param("productId") Long productId);
    
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.productId = :productId")
    Long sumQuantityByProductId(@Param("productId") Long productId);
    
    @Query("SELECT SUM(oi.totalPrice) FROM OrderItem oi WHERE oi.productId = :productId")
    BigDecimal sumTotalPriceByProductId(@Param("productId") Long productId);
    
    @Query("SELECT oi.productId, SUM(oi.quantity) as totalQuantity FROM OrderItem oi GROUP BY oi.productId ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts();
}
