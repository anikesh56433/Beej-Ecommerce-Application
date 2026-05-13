package com.beej.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "display_name", length = 150)
    private String displayName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "profile_picture_url", length = 500)
    private String profilePictureUrl;

    @Column(name = "cover_photo_url", length = 500)
    private String coverPhotoUrl;

    @Column(name = "website", length = 255)
    private String website;

    @Column(name = "occupation", length = 100)
    private String occupation;

    @Column(name = "company", length = 100)
    private String company;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "language", length = 10)
    @Builder.Default
    private String language = "en";

    @Column(name = "currency", length = 3)
    @Builder.Default
    private String currency = "USD";

    @Column(name = "newsletter_subscribed", nullable = false)
    @Builder.Default
    private Boolean newsletterSubscribed = false;

    @Column(name = "marketing_emails_enabled", nullable = false)
    @Builder.Default
    private Boolean marketingEmailsEnabled = true;

    @Column(name = "order_notifications_enabled", nullable = false)
    @Builder.Default
    private Boolean orderNotificationsEnabled = true;

    @Column(name = "promotion_notifications_enabled", nullable = false)
    @Builder.Default
    private Boolean promotionNotificationsEnabled = true;

    @Column(name = "two_factor_enabled", nullable = false)
    @Builder.Default
    private Boolean twoFactorEnabled = false;

    @Column(name = "two_factor_secret", length = 255)
    private String twoFactorSecret;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_login_ip", length = 45)
    private String lastLoginIp;

    @Column(name = "login_count", nullable = false)
    @Builder.Default
    private Integer loginCount = 0;

    @Column(name = "account_status", length = 50)
    @Builder.Default
    private String accountStatus = "ACTIVE";

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
