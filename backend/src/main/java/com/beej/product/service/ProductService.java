package com.beej.product.service;

import com.beej.product.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    
    Page<ProductResponseDTO> getProducts(Long categoryId, String search, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    ProductResponseDTO getProductById(Long id);
    
    List<ProductResponseDTO> getFeaturedProducts();
    
    Page<ProductResponseDTO> searchProducts(String query, Pageable pageable);
    
    List<ProductResponseDTO> getNewArrivals();
    
    List<ProductResponseDTO> getTrendingProducts();
}
