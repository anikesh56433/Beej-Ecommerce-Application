package com.beej.notification.entity;

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
@Table(name = "notification_preferences")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class NotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "is_email_enabled", nullable = false)
    @Builder.Default
    private Boolean isEmailEnabled = true;

    @Column(name = "is_push_enabled", nullable = false)
    @Builder.Default
    private Boolean isPushEnabled = true;

    @Column(name = "is_sms_enabled", nullable = false)
    @Builder.Default
    private Boolean isSmsEnabled = false;

    @Column(name = "is_in_app_enabled", nullable = false)
    @Builder.Default
    private Boolean isInAppEnabled = true;

    @Column(name = "frequency", length = 20)
    @Builder.Default
    private String frequency = "IMMEDIATE";

    @Column(name = "quiet_hours_enabled")
    @Builder.Default
    private Boolean quietHoursEnabled = false;

    @Column(name = "quiet_hours_start", length = 5)
    @Builder.Default
    private String quietHoursStart = "22:00";

    @Column(name = "quiet_hours_end", length = 5)
    @Builder.Default
    private String quietHoursEnd = "08:00";

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
