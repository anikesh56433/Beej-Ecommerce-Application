package com.beej.admin.service;

import com.beej.admin.dto.AdminActivityDTO;
import com.beej.admin.entity.AdminActivity;
import com.beej.admin.mapper.AdminActivityMapper;
import com.beej.admin.repository.AdminActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminActivityServiceImpl implements AdminActivityService {

    private final AdminActivityRepository activityRepository;
    private final AdminActivityMapper activityMapper;

    @Override
    @Transactional
    public AdminActivityDTO logActivity(AdminActivityDTO activityDTO) {
        log.info("Logging admin activity: {} by admin: {}", activityDTO.getAction(), activityDTO.getAdminUsername());
        
        AdminActivity activity = activityMapper.toEntity(activityDTO);
        activity.setCreatedAt(LocalDateTime.now());
        
        AdminActivity saved = activityRepository.save(activity);
        return activityMapper.toAdminActivityDTO(saved);
    }

    @Override
    public Page<AdminActivityDTO> getActivities(Pageable pageable) {
        log.info("Fetching all admin activities");
        return activityRepository.findAll(pageable)
                .map(activityMapper::toAdminActivityDTO);
    }

    @Override
    public Page<AdminActivityDTO> getActivitiesByAdmin(Long adminId, Pageable pageable) {
        log.info("Fetching activities for admin ID: {}", adminId);
        return activityRepository.findByAdminIdOrderByCreatedAtDesc(adminId, pageable)
                .map(activityMapper::toAdminActivityDTO);
    }

    @Override
    public Page<AdminActivityDTO> getActivitiesByAction(String action, Pageable pageable) {
        log.info("Fetching activities with action: {}", action);
        return activityRepository.findByActionOrderByCreatedAtDesc(action, pageable)
                .map(activityMapper::toAdminActivityDTO);
    }

    @Override
    public Page<AdminActivityDTO> getActivitiesByEntityType(String entityType, Pageable pageable) {
        log.info("Fetching activities for entity type: {}", entityType);
        return activityRepository.findByEntityTypeOrderByCreatedAtDesc(entityType, pageable)
                .map(activityMapper::toAdminActivityDTO);
    }

    @Override
    public Page<AdminActivityDTO> searchActivities(String username, Pageable pageable) {
        log.info("Searching activities for username: {}", username);
        return activityRepository.findByAdminUsernameContaining(username, pageable)
                .map(activityMapper::toAdminActivityDTO);
    }

    @Override
    public Page<AdminActivityDTO> getActivitiesByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.info("Fetching activities between {} and {}", startDate, endDate);
        return activityRepository.findByDateRange(startDate, endDate, pageable)
                .map(activityMapper::toAdminActivityDTO);
    }

    @Override
    public Page<AdminActivityDTO> getFailedActivities(Pageable pageable) {
        log.info("Fetching failed activities");
        return activityRepository.findFailedActivities(pageable)
                .map(activityMapper::toAdminActivityDTO);
    }

    @Override
    @Transactional
    public void deleteActivity(Long id) {
        log.info("Deleting activity with ID: {}", id);
        activityRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void clearOldActivities(LocalDateTime beforeDate) {
        log.info("Clearing activities before {}", beforeDate);
        activityRepository.deleteAll();
    }
}
