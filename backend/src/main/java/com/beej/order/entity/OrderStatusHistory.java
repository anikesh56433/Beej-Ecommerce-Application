package com.beej.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "previous_status", length = 50)
    private String previousStatus;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "changed_by", length = 255)
    private String changedBy;

    @Column(name = "changed_by_id")
    private Long changedById;

    @Column(name = "is_customer_visible", nullable = false)
    private Boolean isCustomerVisible = true;

    @Column(name = "notify_customer", nullable = false)
    private Boolean notifyCustomer = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
