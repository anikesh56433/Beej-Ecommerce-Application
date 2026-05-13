package com.beej.user.repository;

import com.beej.user.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    
    Optional<UserProfile> findByUserId(Long userId);
    
    Optional<UserProfile> findByUsername(String username);
    
    Optional<UserProfile> findByEmail(String email);
    
    @Query("SELECT up FROM UserProfile up WHERE up.email LIKE %:email%")
    List<UserProfile> findByEmailContaining(@Param("email") String email);
    
    @Query("SELECT up FROM UserProfile up WHERE up.firstName LIKE %:firstName% OR up.lastName LIKE %:lastName%")
    List<UserProfile> findByNameContaining(@Param("firstName") String firstName, @Param("lastName") String lastName);
    
    @Query("SELECT up FROM UserProfile up WHERE up.firstName LIKE %:search% OR up.lastName LIKE %:search% OR up.username LIKE %:search% OR up.email LIKE %:search%")
    Page<UserProfile> searchProfiles(@Param("search") String search, Pageable pageable);
    
    Page<UserProfile> findByAccountStatus(String accountStatus, Pageable pageable);
    
    @Query("SELECT up FROM UserProfile up WHERE up.newsletterSubscribed = true")
    List<UserProfile> findNewsletterSubscribers();
    
    @Query("SELECT up FROM UserProfile up WHERE up.marketingEmailsEnabled = true")
    List<UserProfile> findMarketingEmailSubscribers();
    
    @Query("SELECT up FROM UserProfile up WHERE up.twoFactorEnabled = true")
    List<UserProfile> findTwoFactorEnabledUsers();
    
    @Query("SELECT up FROM UserProfile up WHERE up.lastLoginAt < :date")
    List<UserProfile> findInactiveUsers(@Param("date") LocalDateTime date);
    
    @Query("SELECT up FROM UserProfile up WHERE up.lastLoginAt > :date")
    List<UserProfile> findRecentlyActiveUsers(@Param("date") LocalDateTime date);
    
    List<UserProfile> findTop10ByOrderByCreatedAtDesc();
    
    @Query("SELECT DISTINCT up.location FROM UserProfile up WHERE up.location IS NOT NULL")
    List<String> findDistinctCountries();
    
    @Query("SELECT DISTINCT up.language FROM UserProfile up WHERE up.language IS NOT NULL")
    List<String> findDistinctLanguages();
    
    Long countByAccountStatus(String accountStatus);
    
    @Query("SELECT COUNT(up) FROM UserProfile up WHERE up.newsletterSubscribed = true")
    Long countNewsletterSubscribers();
    
    @Query("SELECT COUNT(up) FROM UserProfile up WHERE up.twoFactorEnabled = true")
    Long countTwoFactorEnabledUsers();
}
