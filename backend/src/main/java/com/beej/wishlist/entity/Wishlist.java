package com.beej.wishlist.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "wishlists")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "is_private", nullable = false)
    @Builder.Default
    private Boolean isPrivate = true;

    @Column(name = "share_token", length = 255)
    private String shareToken;

    @Column(name = "item_count", nullable = false)
    @Builder.Default
    private Integer itemCount = 0;

    @Column(name = "total_value")
    @Builder.Default
    private java.math.BigDecimal totalValue = java.math.BigDecimal.ZERO;

    @Column(name = "notify_on_price_drop", nullable = false)
    @Builder.Default
    private Boolean notifyOnPriceDrop = true;

    @Column(name = "notify_on_back_in_stock", nullable = false)
    @Builder.Default
    private Boolean notifyOnBackInStock = true;

    @Column(name = "last_viewed_at")
    private LocalDateTime lastViewedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WishlistItem> items;

    @Version
    private Long version;
}
