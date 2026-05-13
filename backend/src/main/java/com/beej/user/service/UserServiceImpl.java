package com.beej.user.service;

import com.beej.user.dto.*;
import com.beej.user.entity.*;
import com.beej.user.mapper.*;
import com.beej.user.repository.*;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserActivityLogRepository userActivityLogRepository;
    private final UserProfileMapper userProfileMapper;
    private final UserAddressMapper userAddressMapper;
    private final UserActivityLogMapper userActivityLogMapper;

    @Override
    @Transactional
    public UserProfileDTO createUserProfile(CreateUserProfileRequestDTO request) {
        log.info("Creating user profile for user: {}", request.getUserId());
        
        UserProfile profile = UserProfile.builder()
                .userId(request.getUserId())
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .displayName(request.getDisplayName())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .bio(request.getBio())
                .profilePictureUrl(request.getProfilePictureUrl())
                .coverPhotoUrl(request.getCoverPhotoUrl())
                .website(request.getWebsite())
                .occupation(request.getOccupation())
                .company(request.getCompany())
                .location(request.getLocation())
                .timezone(request.getTimezone())
                .language(request.getLanguage())
                .currency(request.getCurrency())
                .newsletterSubscribed(false)
                .marketingEmailsEnabled(true)
                .orderNotificationsEnabled(true)
                .promotionNotificationsEnabled(true)
                .twoFactorEnabled(false)
                .loginCount(0)
                .accountStatus("ACTIVE")
                .build();
        
        UserProfile saved = userProfileRepository.save(profile);
        log.info("Created user profile with ID: {}", saved.getId());
        return userProfileMapper.toUserProfileDTO(saved);
    }

    @Override
    public UserProfileDTO getUserProfileById(Long id) {
        log.info("Fetching user profile with ID: {}", id);
        return userProfileRepository.findById(id)
                .map(userProfileMapper::toUserProfileDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found with id: " + id));
    }

    @Override
    public UserProfileDTO getUserProfileByUserId(Long userId) {
        log.info("Fetching user profile for user: {}", userId);
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + userId));
        List<UserAddress> addresses = userAddressRepository.findByUserIdAndIsActiveTrue(userId);
        return userProfileMapper.toUserProfileDTOWithAddresses(profile, addresses);
    }

    @Override
    public UserProfileDTO getUserProfileByUsername(String username) {
        log.info("Fetching user profile by username: {}", username);
        return userProfileRepository.findByUsername(username)
                .map(userProfileMapper::toUserProfileDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found with username: " + username));
    }

    @Override
    public UserProfileDTO getUserProfileByEmail(String email) {
        log.info("Fetching user profile by email: {}", email);
        return userProfileRepository.findByEmail(email)
                .map(userProfileMapper::toUserProfileDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found with email: " + email));
    }

    @Override
    public List<UserProfileDTO> getAllUserProfiles() {
        log.info("Fetching all user profiles");
        return userProfileRepository.findAll()
                .stream()
                .map(userProfileMapper::toUserProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserProfileDTO> searchUserProfiles(String search) {
        log.info("Searching user profiles with query: {}", search);
        return userProfileRepository.searchProfiles(search, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(userProfileMapper::toUserProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserProfileDTO> getUserProfilesByAccountStatus(String accountStatus) {
        log.info("Fetching user profiles with status: {}", accountStatus);
        return userProfileRepository.findByAccountStatus(accountStatus, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(userProfileMapper::toUserProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserProfileDTO updateUserProfile(Long id, UpdateUserProfileRequestDTO request) {
        log.info("Updating user profile with ID: {}", id);
        
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found with id: " + id));
        
        updateProfileFields(profile, request);
        
        UserProfile saved = userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileDTO(saved);
    }

    @Override
    @Transactional
    public UserProfileDTO updateUserProfileByUserId(Long userId, UpdateUserProfileRequestDTO request) {
        log.info("Updating user profile for user: {}", userId);
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + userId));
        
        updateProfileFields(profile, request);
        
        UserProfile saved = userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileDTO(saved);
    }

    private void updateProfileFields(UserProfile profile, UpdateUserProfileRequestDTO request) {
        if (request.getFirstName() != null) profile.setFirstName(request.getFirstName());
        if (request.getLastName() != null) profile.setLastName(request.getLastName());
        if (request.getDisplayName() != null) profile.setDisplayName(request.getDisplayName());
        if (request.getPhoneNumber() != null) profile.setPhoneNumber(request.getPhoneNumber());
        if (request.getDateOfBirth() != null) profile.setDateOfBirth(request.getDateOfBirth());
        if (request.getGender() != null) profile.setGender(request.getGender());
        if (request.getBio() != null) profile.setBio(request.getBio());
        if (request.getProfilePictureUrl() != null) profile.setProfilePictureUrl(request.getProfilePictureUrl());
        if (request.getCoverPhotoUrl() != null) profile.setCoverPhotoUrl(request.getCoverPhotoUrl());
        if (request.getWebsite() != null) profile.setWebsite(request.getWebsite());
        if (request.getOccupation() != null) profile.setOccupation(request.getOccupation());
        if (request.getCompany() != null) profile.setCompany(request.getCompany());
        if (request.getLocation() != null) profile.setLocation(request.getLocation());
        if (request.getTimezone() != null) profile.setTimezone(request.getTimezone());
        if (request.getLanguage() != null) profile.setLanguage(request.getLanguage());
        if (request.getCurrency() != null) profile.setCurrency(request.getCurrency());
        if (request.getNewsletterSubscribed() != null) profile.setNewsletterSubscribed(request.getNewsletterSubscribed());
        if (request.getMarketingEmailsEnabled() != null) profile.setMarketingEmailsEnabled(request.getMarketingEmailsEnabled());
        if (request.getOrderNotificationsEnabled() != null) profile.setOrderNotificationsEnabled(request.getOrderNotificationsEnabled());
        if (request.getPromotionNotificationsEnabled() != null) profile.setPromotionNotificationsEnabled(request.getPromotionNotificationsEnabled());
    }

    @Override
    @Transactional
    public void deleteUserProfile(Long id) {
        log.info("Deleting user profile with ID: {}", id);
        
        if (!userProfileRepository.existsById(id)) {
            throw new ResourceNotFoundException("User profile not found with id: " + id);
        }
        
        userProfileRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUserProfileByUserId(Long userId) {
        log.info("Deleting user profile for user: {}", userId);
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + userId));
        
        userProfileRepository.delete(profile);
    }

    @Override
    @Transactional
    public UserProfileDTO activateUserProfile(Long userId) {
        log.info("Activating user profile for user: {}", userId);
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + userId));
        
        profile.setAccountStatus("ACTIVE");
        UserProfile saved = userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileDTO(saved);
    }

    @Override
    @Transactional
    public UserProfileDTO deactivateUserProfile(Long userId) {
        log.info("Deactivating user profile for user: {}", userId);
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + userId));
        
        profile.setAccountStatus("INACTIVE");
        UserProfile saved = userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileDTO(saved);
    }

    @Override
    @Transactional
    public UserProfileDTO suspendUserProfile(Long userId) {
        log.info("Suspending user profile for user: {}", userId);
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + userId));
        
        profile.setAccountStatus("SUSPENDED");
        UserProfile saved = userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileDTO(saved);
    }

    @Override
    @Transactional
    public UserProfileDTO enableTwoFactor(Long userId) {
        log.info("Enabling 2FA for user: {}", userId);
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + userId));
        
        profile.setTwoFactorEnabled(true);
        UserProfile saved = userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileDTO(saved);
    }

    @Override
    @Transactional
    public UserProfileDTO disableTwoFactor(Long userId) {
        log.info("Disabling 2FA for user: {}", userId);
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + userId));
        
        profile.setTwoFactorEnabled(false);
        profile.setTwoFactorSecret(null);
        UserProfile saved = userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileDTO(saved);
    }

    @Override
    @Transactional
    public void recordLogin(Long userId, String ipAddress) {
        log.info("Recording login for user: {} from IP: {}", userId, ipAddress);
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + userId));
        
        profile.setLastLoginAt(LocalDateTime.now());
        profile.setLastLoginIp(ipAddress);
        profile.setLoginCount(profile.getLoginCount() + 1);
        
        userProfileRepository.save(profile);
    }

    @Override
    public List<UserProfileDTO> getNewsletterSubscribers() {
        log.info("Fetching newsletter subscribers");
        return userProfileRepository.findNewsletterSubscribers()
                .stream()
                .map(userProfileMapper::toUserProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserProfileDTO> getMarketingEmailSubscribers() {
        log.info("Fetching marketing email subscribers");
        return userProfileRepository.findMarketingEmailSubscribers()
                .stream()
                .map(userProfileMapper::toUserProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserProfileDTO> getTwoFactorEnabledUsers() {
        log.info("Fetching 2FA enabled users");
        return userProfileRepository.findTwoFactorEnabledUsers()
                .stream()
                .map(userProfileMapper::toUserProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserProfileDTO> getInactiveUsers(int days) {
        log.info("Fetching users inactive for {} days", days);
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        return userProfileRepository.findInactiveUsers(cutoff)
                .stream()
                .map(userProfileMapper::toUserProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserProfileDTO> getRecentlyActiveUsers(int days) {
        log.info("Fetching users active in last {} days", days);
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        return userProfileRepository.findRecentlyActiveUsers(cutoff)
                .stream()
                .map(userProfileMapper::toUserProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long countByAccountStatus(String accountStatus) {
        return userProfileRepository.countByAccountStatus(accountStatus);
    }

    @Override
    public Long countNewsletterSubscribers() {
        return userProfileRepository.countNewsletterSubscribers();
    }

    @Override
    public Long countTwoFactorEnabledUsers() {
        return userProfileRepository.countTwoFactorEnabledUsers();
    }

    @Override
    public List<String> getDistinctCountries() {
        return userProfileRepository.findDistinctCountries();
    }

    @Override
    public List<String> getDistinctLanguages() {
        return userProfileRepository.findDistinctLanguages();
    }

    @Override
    @Transactional
    public UserAddressDTO addUserAddress(CreateUserAddressRequestDTO request) {
        log.info("Adding address for user: {}", request.getUserId());
        
        UserAddress address = UserAddress.builder()
                .userId(request.getUserId())
                .addressType(request.getAddressType())
                .isDefault(request.getIsDefault())
                .recipientName(request.getRecipientName())
                .recipientPhone(request.getRecipientPhone())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .countryCode(request.getCountryCode())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .deliveryInstructions(request.getDeliveryInstructions())
                .isActive(true)
                .build();
        
        if (request.getIsDefault()) {
            userAddressRepository.unsetOtherDefaultAddresses(request.getUserId(), null);
        }
        
        UserAddress saved = userAddressRepository.save(address);
        log.info("Added address with ID: {}", saved.getId());
        return userAddressMapper.toUserAddressDTO(saved);
    }

    @Override
    public UserAddressDTO getUserAddressById(Long id) {
        log.info("Fetching address with ID: {}", id);
        return userAddressRepository.findById(id)
                .map(userAddressMapper::toUserAddressDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    @Override
    public List<UserAddressDTO> getUserAddresses(Long userId) {
        log.info("Fetching addresses for user: {}", userId);
        return userAddressRepository.findByUserId(userId)
                .stream()
                .map(userAddressMapper::toUserAddressDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAddressDTO> getActiveUserAddresses(Long userId) {
        log.info("Fetching active addresses for user: {}", userId);
        return userAddressRepository.findByUserIdAndIsActiveTrue(userId)
                .stream()
                .map(userAddressMapper::toUserAddressDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserAddressDTO getDefaultUserAddress(Long userId) {
        log.info("Fetching default address for user: {}", userId);
        return userAddressRepository.findByUserIdAndIsDefaultTrue(userId)
                .map(userAddressMapper::toUserAddressDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public UserAddressDTO updateUserAddress(Long id, UpdateUserAddressRequestDTO request) {
        log.info("Updating address with ID: {}", id);
        
        UserAddress address = userAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        
        if (request.getAddressType() != null) address.setAddressType(request.getAddressType());
        if (request.getIsDefault() != null) address.setIsDefault(request.getIsDefault());
        if (request.getRecipientName() != null) address.setRecipientName(request.getRecipientName());
        if (request.getRecipientPhone() != null) address.setRecipientPhone(request.getRecipientPhone());
        if (request.getAddressLine1() != null) address.setAddressLine1(request.getAddressLine1());
        if (request.getAddressLine2() != null) address.setAddressLine2(request.getAddressLine2());
        if (request.getCity() != null) address.setCity(request.getCity());
        if (request.getState() != null) address.setState(request.getState());
        if (request.getPostalCode() != null) address.setPostalCode(request.getPostalCode());
        if (request.getCountry() != null) address.setCountry(request.getCountry());
        if (request.getCountryCode() != null) address.setCountryCode(request.getCountryCode());
        if (request.getLatitude() != null) address.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) address.setLongitude(request.getLongitude());
        if (request.getDeliveryInstructions() != null) address.setDeliveryInstructions(request.getDeliveryInstructions());
        if (request.getIsActive() != null) address.setIsActive(request.getIsActive());
        
        if (request.getIsDefault() != null && request.getIsDefault()) {
            userAddressRepository.unsetOtherDefaultAddresses(address.getUserId(), id);
        }
        
        UserAddress saved = userAddressRepository.save(address);
        return userAddressMapper.toUserAddressDTO(saved);
    }

    @Override
    @Transactional
    public UserAddressDTO setDefaultAddress(Long userId, Long addressId) {
        log.info("Setting address {} as default for user: {}", addressId, userId);
        
        userAddressRepository.unsetOtherDefaultAddresses(userId, addressId);
        
        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
        
        address.setIsDefault(true);
        UserAddress saved = userAddressRepository.save(address);
        return userAddressMapper.toUserAddressDTO(saved);
    }

    @Override
    @Transactional
    public void deleteUserAddress(Long id) {
        log.info("Deleting address with ID: {}", id);
        
        if (!userAddressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        
        userAddressRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllUserAddresses(Long userId) {
        log.info("Deleting all addresses for user: {}", userId);
        userAddressRepository.deleteByUserId(userId);
    }

    @Override
    public Long countUserAddresses(Long userId) {
        return userAddressRepository.countByUserIdAndIsActiveTrue(userId);
    }

    @Override
    @Transactional
    public void logUserActivity(Long userId, String activityType, String activityDescription, 
                                String ipAddress, String userAgent, String deviceInfo) {
        log.info("Logging activity for user: {} - {}", userId, activityType);
        
        UserActivityLog activityLog = UserActivityLog.builder()
                .userId(userId)
                .activityType(activityType)
                .activityDescription(activityDescription)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .deviceInfo(deviceInfo)
                .status("SUCCESS")
                .build();
        
        userActivityLogRepository.save(activityLog);
    }

    @Override
    public List<UserActivityLogDTO> getUserActivityLogs(Long userId) {
        log.info("Fetching activity logs for user: {}", userId);
        return userActivityLogRepository.findByUserIdOrderByCreatedAtDesc(userId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(userActivityLogMapper::toUserActivityLogDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserActivityLogDTO> getUserActivityLogsByType(Long userId, String activityType) {
        log.info("Fetching activity logs for user: {} with type: {}", userId, activityType);
        return userActivityLogRepository.findByUserIdAndActivityType(userId, activityType)
                .stream()
                .map(userActivityLogMapper::toUserActivityLogDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cleanupOldActivityLogs(int days) {
        log.info("Cleaning up activity logs older than {} days", days);
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        userActivityLogRepository.deleteOldActivityLogs(cutoff);
    }
}
