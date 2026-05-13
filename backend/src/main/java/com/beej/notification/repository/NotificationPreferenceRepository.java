package com.beej.notification.repository;

import com.beej.notification.entity.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long> {
    
    List<NotificationPreference> findByUserId(Long userId);
    
    Optional<NotificationPreference> findByUserIdAndCategory(Long userId, String category);
    
    @Query("SELECT n FROM NotificationPreference n WHERE n.userId = :userId AND n.isInAppEnabled = true")
    List<NotificationPreference> findInAppEnabledPreferences(@Param("userId") Long userId);
    
    @Query("SELECT n FROM NotificationPreference n WHERE n.userId = :userId AND n.isEmailEnabled = true")
    List<NotificationPreference> findEmailEnabledPreferences(@Param("userId") Long userId);
    
    @Query("SELECT n FROM NotificationPreference n WHERE n.userId = :userId AND n.isPushEnabled = true")
    List<NotificationPreference> findPushEnabledPreferences(@Param("userId") Long userId);
    
    @Query("SELECT n FROM NotificationPreference n WHERE n.userId = :userId AND n.isSmsEnabled = true")
    List<NotificationPreference> findSmsEnabledPreferences(@Param("userId") Long userId);
    
    void deleteByUserId(Long userId);
    
    @Query("SELECT DISTINCT n.category FROM NotificationPreference n WHERE n.userId = :userId")
    List<String> findCategoriesByUserId(@Param("userId") Long userId);
}
