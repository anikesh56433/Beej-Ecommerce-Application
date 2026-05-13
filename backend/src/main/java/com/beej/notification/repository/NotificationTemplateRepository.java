package com.beej.notification.repository;

import com.beej.notification.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    
    Optional<NotificationTemplate> findByName(String name);
    
    List<NotificationTemplate> findByType(String type);
    
    List<NotificationTemplate> findByCategory(String category);
    
    List<NotificationTemplate> findByIsActiveTrue();
    
    List<NotificationTemplate> findByTypeAndIsActiveTrue(String type);
    
    List<NotificationTemplate> findByCategoryAndIsActiveTrue(String category);
    
    @Query("SELECT DISTINCT n.category FROM NotificationTemplate n WHERE n.isActive = true")
    List<String> findActiveCategories();
    
    @Query("SELECT DISTINCT n.type FROM NotificationTemplate n WHERE n.isActive = true")
    List<String> findActiveTypes();
    
    @Query("SELECT n FROM NotificationTemplate n WHERE n.isActive = true ORDER BY n.category, n.name")
    List<NotificationTemplate> findActiveTemplatesOrderByCategoryAndName();
}
