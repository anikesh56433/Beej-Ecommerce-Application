package com.beej.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_dashboard_stats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AdminDashboardStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_users")
    private Long totalUsers;

    @Column(name = "total_products")
    private Long totalProducts;

    @Column(name = "total_orders")
    private Long totalOrders;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @Column(name = "active_users")
    private Long activeUsers;

    @Column(name = "pending_orders")
    private Long pendingOrders;

    @Column(name = "processing_orders")
    private Long processingOrders;

    @Column(name = "shipped_orders")
    private Long shippedOrders;

    @Column(name = "delivered_orders")
    private Long deliveredOrders;

    @Column(name = "cancelled_orders")
    private Long cancelledOrders;

    @Column(name = "low_stock_products")
    private Long lowStockProducts;

    @Column(name = "out_of_stock_products")
    private Long outOfStockProducts;

    @Column(name = "recent_signups", length = 1000)
    private String recentSignups;

    @Column(name = "top_selling_products", length = 1000)
    private String topSellingProducts;

    @Column(name = "revenue_by_period", columnDefinition = "TEXT")
    private String revenueByPeriod;

    @Column(name = "orders_by_status", columnDefinition = "TEXT")
    private String ordersByStatus;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
