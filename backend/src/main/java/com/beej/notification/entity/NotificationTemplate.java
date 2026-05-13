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
@Table(name = "notification_templates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "priority", length = 20)
    private String priority = "NORMAL";

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_email_enabled")
    private Boolean isEmailEnabled = false;

    @Column(name = "is_push_enabled")
    private Boolean isPushEnabled = false;

    @Column(name = "is_sms_enabled")
    private Boolean isSmsEnabled = false;

    @Column(name = "email_subject", length = 255)
    private String emailSubject;

    @Column(name = "email_template", columnDefinition = "TEXT")
    private String emailTemplate;

    @Column(name = "sms_template", length = 1000)
    private String smsTemplate;

    @Column(name = "push_template", length = 1000)
    private String pushTemplate;

    @Column(name = "variables", columnDefinition = "TEXT")
    private String variables;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
