package com.beej.wishlist.entity;

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
@Table(name = "wishlist_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", length = 255)
    private String productName;

    @Column(name = "product_sku", length = 100)
    private String productSku;

    @Column(name = "product_image", length = 500)
    private String productImage;

    @Column(name = "product_url", length = 500)
    private String productUrl;

    @Column(name = "quantity", nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "added_price", precision = 10, scale = 2)
    private BigDecimal addedPrice;

    @Column(name = "price_change")
    private BigDecimal priceChange;

    @Column(name = "is_price_dropped", nullable = false)
    @Builder.Default
    private Boolean isPriceDropped = false;

    @Column(name = "is_on_sale", nullable = false)
    @Builder.Default
    private Boolean isOnSale = false;

    @Column(name = "sale_price", precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "priority", length = 20)
    @Builder.Default
    private String priority = "NORMAL";

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "is_available", nullable = false)
    @Builder.Default
    private Boolean isAvailable = true;

    @Column(name = "is_notified", nullable = false)
    @Builder.Default
    private Boolean isNotified = false;

    @Column(name = "notified_at")
    private LocalDateTime notifiedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
