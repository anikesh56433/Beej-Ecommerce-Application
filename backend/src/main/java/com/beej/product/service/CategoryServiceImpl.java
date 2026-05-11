package com.beej.product.service;

import com.beej.product.dto.CategoryDTO;
import com.beej.product.entity.Category;
import com.beej.product.mapper.CategoryMapper;
import com.beej.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toCategoryDTO)
                .toList();
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new com.beej.exception.ResourceNotFoundException("Category not found with id: " + id));
        
        return categoryMapper.toCategoryDTO(category);
    }
}
