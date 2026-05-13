package com.beej.payment.repository;

import com.beej.payment.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    
    List<PaymentMethod> findByUserId(Long userId);
    
    Optional<PaymentMethod> findByUserIdAndIsDefaultTrue(Long userId);
    
    Optional<PaymentMethod> findByUserIdAndMethodIdentifier(Long userId, String methodIdentifier);
    
    List<PaymentMethod> findByUserIdAndIsActiveTrue(Long userId);
    
    List<PaymentMethod> findByMethodType(String methodType);
    
    List<PaymentMethod> findByProvider(String provider);
    
    @Query("SELECT p FROM PaymentMethod p WHERE p.gatewayCustomerId = :gatewayCustomerId")
    List<PaymentMethod> findByGatewayCustomerId(@Param("gatewayCustomerId") String gatewayCustomerId);
    
    @Query("SELECT DISTINCT p.methodType FROM PaymentMethod p WHERE p.isActive = true")
    List<String> findDistinctActiveMethodTypes();
    
    @Query("SELECT DISTINCT p.provider FROM PaymentMethod p WHERE p.isActive = true")
    List<String> findDistinctActiveProviders();
    
    void deleteByUserId(Long userId);
    
    @Query("UPDATE PaymentMethod p SET p.isDefault = false WHERE p.userId = :userId AND p.id != :id")
    void unsetOtherDefaultMethods(@Param("userId") Long userId, @Param("id") Long id);
}
