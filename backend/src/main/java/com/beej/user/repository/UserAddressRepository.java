package com.beej.user.repository;

import com.beej.user.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    
    List<UserAddress> findByUserId(Long userId);
    
    List<UserAddress> findByUserIdAndIsActiveTrue(Long userId);
    
    Optional<UserAddress> findByUserIdAndIsDefaultTrue(Long userId);
    
    List<UserAddress> findByUserIdAndAddressType(Long userId, String addressType);
    
    @Query("SELECT ua FROM UserAddress ua WHERE ua.userId = :userId AND ua.isActive = true AND ua.addressType = :addressType")
    List<UserAddress> findActiveAddressesByType(@Param("userId") Long userId, @Param("addressType") String addressType);
    
    @Query("SELECT ua FROM UserAddress ua WHERE ua.userId = :userId AND ua.isDefault = true")
    Optional<UserAddress> findDefaultAddressByUserId(@Param("userId") Long userId);
    
    @Query("UPDATE UserAddress ua SET ua.isDefault = false WHERE ua.userId = :userId AND ua.id != :id")
    void unsetOtherDefaultAddresses(@Param("userId") Long userId, @Param("id") Long id);
    
    Long countByUserId(Long userId);
    
    Long countByUserIdAndIsActiveTrue(Long userId);
    
    @Query("SELECT DISTINCT ua.country FROM UserAddress ua WHERE ua.country IS NOT NULL")
    List<String> findDistinctCountries();
    
    @Query("SELECT DISTINCT ua.city FROM UserAddress ua WHERE ua.city IS NOT NULL")
    List<String> findDistinctCities();
    
    void deleteByUserId(Long userId);
}
