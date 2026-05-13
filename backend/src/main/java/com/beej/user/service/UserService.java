package com.beej.user.service;

import com.beej.user.dto.*;

import java.util.List;

public interface UserService {
    
    UserProfileDTO createUserProfile(CreateUserProfileRequestDTO request);
    
    UserProfileDTO getUserProfileById(Long id);
    
    UserProfileDTO getUserProfileByUserId(Long userId);
    
    UserProfileDTO getUserProfileByUsername(String username);
    
    UserProfileDTO getUserProfileByEmail(String email);
    
    List<UserProfileDTO> getAllUserProfiles();
    
    List<UserProfileDTO> searchUserProfiles(String search);
    
    List<UserProfileDTO> getUserProfilesByAccountStatus(String accountStatus);
    
    UserProfileDTO updateUserProfile(Long id, UpdateUserProfileRequestDTO request);
    
    UserProfileDTO updateUserProfileByUserId(Long userId, UpdateUserProfileRequestDTO request);
    
    void deleteUserProfile(Long id);
    
    void deleteUserProfileByUserId(Long userId);
    
    UserProfileDTO activateUserProfile(Long userId);
    
    UserProfileDTO deactivateUserProfile(Long userId);
    
    UserProfileDTO suspendUserProfile(Long userId);
    
    UserProfileDTO enableTwoFactor(Long userId);
    
    UserProfileDTO disableTwoFactor(Long userId);
    
    void recordLogin(Long userId, String ipAddress);
    
    List<UserProfileDTO> getNewsletterSubscribers();
    
    List<UserProfileDTO> getMarketingEmailSubscribers();
    
    List<UserProfileDTO> getTwoFactorEnabledUsers();
    
    List<UserProfileDTO> getInactiveUsers(int days);
    
    List<UserProfileDTO> getRecentlyActiveUsers(int days);
    
    Long countByAccountStatus(String accountStatus);
    
    Long countNewsletterSubscribers();
    
    Long countTwoFactorEnabledUsers();
    
    List<String> getDistinctCountries();
    
    List<String> getDistinctLanguages();
    
    UserAddressDTO addUserAddress(CreateUserAddressRequestDTO request);
    
    UserAddressDTO getUserAddressById(Long id);
    
    List<UserAddressDTO> getUserAddresses(Long userId);
    
    List<UserAddressDTO> getActiveUserAddresses(Long userId);
    
    UserAddressDTO getDefaultUserAddress(Long userId);
    
    UserAddressDTO updateUserAddress(Long id, UpdateUserAddressRequestDTO request);
    
    UserAddressDTO setDefaultAddress(Long userId, Long addressId);
    
    void deleteUserAddress(Long id);
    
    void deleteAllUserAddresses(Long userId);
    
    Long countUserAddresses(Long userId);
    
    void logUserActivity(Long userId, String activityType, String activityDescription, 
                        String ipAddress, String userAgent, String deviceInfo);
    
    List<UserActivityLogDTO> getUserActivityLogs(Long userId);
    
    List<UserActivityLogDTO> getUserActivityLogsByType(Long userId, String activityType);
    
    void cleanupOldActivityLogs(int days);
}
