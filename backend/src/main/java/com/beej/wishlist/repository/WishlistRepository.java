package com.beej.wishlist.repository;

import com.beej.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
    List<Wishlist> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    List<Wishlist> findByUserIdAndIsPrivateFalseOrderByCreatedAtDesc(Long userId);
    
    Optional<Wishlist> findByUserIdAndIsDefaultTrue(Long userId);
    
    Optional<Wishlist> findByShareToken(String shareToken);
    
    @Query("SELECT w FROM Wishlist w WHERE w.userId = :userId AND w.name = :name")
    Optional<Wishlist> findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);
    
    @Query("UPDATE Wishlist w SET w.isDefault = false WHERE w.userId = :userId AND w.id != :id")
    void unsetOtherDefaultWishlists(@Param("userId") Long userId, @Param("id") Long id);
    
    @Query("SELECT COUNT(w) FROM Wishlist w WHERE w.userId = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(w) FROM Wishlist w WHERE w.userId = :userId AND w.isPrivate = false")
    Long countPublicByUserId(@Param("userId") Long userId);
    
    void deleteByUserId(Long userId);
    
    List<Wishlist> findTop10ByOrderByUpdatedAtDesc();
    
    List<Wishlist> findTop10ByUserIdOrderByUpdatedAtDesc(Long userId);
    
    @Query("SELECT DISTINCT w.userId FROM Wishlist w")
    List<Long> findDistinctUserIds();
}
