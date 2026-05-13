package com.beej.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "method_type", nullable = false, length = 50)
    private String methodType;

    @Column(name = "provider", nullable = false, length = 100)
    private String provider;

    @Column(name = "method_identifier", nullable = false, length = 255)
    private String methodIdentifier;

    @Column(name = "card_last_four", length = 4)
    private String cardLastFour;

    @Column(name = "card_brand", length = 50)
    private String cardBrand;

    @Column(name = "card_expiry_month", length = 2)
    private String cardExpiryMonth;

    @Column(name = "card_expiry_year", length = 4)
    private String cardExpiryYear;

    @Column(name = "cardholder_name", length = 255)
    private String cardholderName;

    @Column(name = "gateway_customer_id", length = 255)
    private String gatewayCustomerId;

    @Column(name = "gateway_payment_method_id", length = 255)
    private String gatewayPaymentMethodId;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "billing_address", columnDefinition = "TEXT")
    private String billingAddress;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
