package com.beej.notification.service;

import com.beej.notification.dto.NotificationTemplateDTO;

import java.util.List;

public interface NotificationTemplateService {
    
    NotificationTemplateDTO createTemplate(NotificationTemplateDTO templateDTO);
    
    NotificationTemplateDTO getTemplateById(Long id);
    
    NotificationTemplateDTO getTemplateByName(String name);
    
    List<NotificationTemplateDTO> getAllTemplates();
    
    List<NotificationTemplateDTO> getTemplatesByType(String type);
    
    List<NotificationTemplateDTO> getTemplatesByCategory(String category);
    
    List<NotificationTemplateDTO> getActiveTemplates();
    
    NotificationTemplateDTO updateTemplate(Long id, NotificationTemplateDTO templateDTO);
    
    void deleteTemplate(Long id);
    
    List<String> getActiveCategories();
    
    List<String> getActiveTypes();
}
