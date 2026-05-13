package com.beej.review.repository;

import com.beej.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    
    List<ReviewImage> findByReviewIdOrderByImageOrderAsc(Long reviewId);
    
    List<ReviewImage> findByReviewIdAndIsPrimaryTrue(Long reviewId);
    
    @Query("SELECT ri FROM ReviewImage ri WHERE ri.review.id = :reviewId ORDER BY ri.imageOrder ASC")
    List<ReviewImage> findByReviewIdOrderByImageOrder(@Param("reviewId") Long reviewId);
    
    void deleteByReviewId(Long reviewId);
    
    @Query("SELECT COUNT(ri) FROM ReviewImage ri WHERE ri.review.id = :reviewId")
    Long countByReviewId(@Param("reviewId") Long reviewId);
}
