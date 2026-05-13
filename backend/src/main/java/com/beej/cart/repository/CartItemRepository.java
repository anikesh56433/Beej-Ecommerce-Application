package com.beej.cart.repository;

import com.beej.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByCartId(Long cartId);
    
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    
    List<CartItem> findByProductId(Long productId);
    
    void deleteByCartId(Long cartId);
    
    void deleteByCartIdAndProductId(Long cartId, Long productId);
}
