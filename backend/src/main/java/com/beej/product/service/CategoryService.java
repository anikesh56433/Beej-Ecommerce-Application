package com.beej.product.service;

import com.beej.product.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    
    List<CategoryDTO> getAllCategories();
    
    CategoryDTO getCategoryById(Long id);
}
