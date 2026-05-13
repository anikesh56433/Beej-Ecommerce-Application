package com.beej.review.repository;

import com.beej.review.entity.ReviewHelpful;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewHelpfulRepository extends JpaRepository<ReviewHelpful, Long> {
    
    Optional<ReviewHelpful> findByReviewIdAndUserId(Long reviewId, Long userId);
    
    List<ReviewHelpful> findByReviewId(Long reviewId);
    
    List<ReviewHelpful> findByUserId(Long userId);
    
    @Query("SELECT COUNT(rh) FROM ReviewHelpful rh WHERE rh.review.id = :reviewId AND rh.isHelpful = true")
    Long countHelpfulByReviewId(@Param("reviewId") Long reviewId);
    
    @Query("SELECT COUNT(rh) FROM ReviewHelpful rh WHERE rh.review.id = :reviewId AND rh.isHelpful = false")
    Long countNotHelpfulByReviewId(@Param("reviewId") Long reviewId);
    
    void deleteByReviewId(Long reviewId);
    
    void deleteByReviewIdAndUserId(Long reviewId, Long userId);
    
    @Query("SELECT COUNT(rh) FROM ReviewHelpful rh WHERE rh.userId = :userId")
    Long countByUserId(@Param("userId") Long userId);
}
