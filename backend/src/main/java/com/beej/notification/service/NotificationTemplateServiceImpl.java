package com.beej.notification.service;

import com.beej.notification.dto.NotificationTemplateDTO;
import com.beej.notification.entity.NotificationTemplate;
import com.beej.notification.mapper.NotificationTemplateMapper;
import com.beej.notification.repository.NotificationTemplateRepository;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private final NotificationTemplateRepository templateRepository;
    private final NotificationTemplateMapper templateMapper;

    @Override
    @Transactional
    public NotificationTemplateDTO createTemplate(NotificationTemplateDTO templateDTO) {
        log.info("Creating notification template: {}", templateDTO.getName());
        
        NotificationTemplate template = templateMapper.toEntity(templateDTO);
        NotificationTemplate saved = templateRepository.save(template);
        
        log.info("Created notification template with ID: {}", saved.getId());
        return templateMapper.toNotificationTemplateDTO(saved);
    }

    @Override
    public NotificationTemplateDTO getTemplateById(Long id) {
        log.info("Fetching notification template with ID: {}", id);
        return templateRepository.findById(id)
                .map(templateMapper::toNotificationTemplateDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + id));
    }

    @Override
    public NotificationTemplateDTO getTemplateByName(String name) {
        log.info("Fetching notification template by name: {}", name);
        return templateRepository.findByName(name)
                .map(templateMapper::toNotificationTemplateDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with name: " + name));
    }

    @Override
    public List<NotificationTemplateDTO> getAllTemplates() {
        log.info("Fetching all notification templates");
        return templateRepository.findAll()
                .stream()
                .map(templateMapper::toNotificationTemplateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationTemplateDTO> getTemplatesByType(String type) {
        log.info("Fetching notification templates by type: {}", type);
        return templateRepository.findByType(type)
                .stream()
                .map(templateMapper::toNotificationTemplateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationTemplateDTO> getTemplatesByCategory(String category) {
        log.info("Fetching notification templates by category: {}", category);
        return templateRepository.findByCategory(category)
                .stream()
                .map(templateMapper::toNotificationTemplateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationTemplateDTO> getActiveTemplates() {
        log.info("Fetching active notification templates");
        return templateRepository.findByIsActiveTrue()
                .stream()
                .map(templateMapper::toNotificationTemplateDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationTemplateDTO updateTemplate(Long id, NotificationTemplateDTO templateDTO) {
        log.info("Updating notification template with ID: {}", id);
        
        NotificationTemplate existing = templateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + id));
        
        NotificationTemplate updated = templateMapper.toEntity(templateDTO);
        updated.setId(id);
        
        NotificationTemplate saved = templateRepository.save(updated);
        log.info("Updated notification template");
        
        return templateMapper.toNotificationTemplateDTO(saved);
    }

    @Override
    @Transactional
    public void deleteTemplate(Long id) {
        log.info("Deleting notification template with ID: {}", id);
        
        if (!templateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Template not found with id: " + id);
        }
        
        templateRepository.deleteById(id);
        log.info("Deleted notification template");
    }

    @Override
    public List<String> getActiveCategories() {
        log.info("Fetching active notification categories");
        return templateRepository.findActiveCategories();
    }

    @Override
    public List<String> getActiveTypes() {
        log.info("Fetching active notification types");
        return templateRepository.findActiveTypes();
    }
}
