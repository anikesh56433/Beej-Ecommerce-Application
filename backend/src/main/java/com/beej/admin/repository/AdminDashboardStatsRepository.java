package com.beej.admin.repository;

import com.beej.admin.entity.AdminDashboardStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDashboardStatsRepository extends JpaRepository<AdminDashboardStats, Long> {
    
    Optional<AdminDashboardStats> findTopByOrderByCreatedAtDesc();
    
    void deleteAll();
}
