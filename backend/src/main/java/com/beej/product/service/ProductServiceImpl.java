package com.beej.product.service;

import com.beej.product.dto.ProductResponseDTO;
import com.beej.product.entity.Product;
import com.beej.product.mapper.ProductMapper;
import com.beej.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductResponseDTO> getProducts(Long categoryId, String search, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Page<Product> products;
        
        if (categoryId != null && minPrice != null && maxPrice != null) {
            products = productRepository.findByCategoryAndPriceBetween(categoryId, minPrice, maxPrice, pageable);
        } else if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId, pageable);
        } else if (search != null && !search.trim().isEmpty()) {
            products = productRepository.findByNameContaining(search.trim(), pageable);
        } else if (minPrice != null && maxPrice != null) {
            products = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else {
            products = productRepository.findByStatus("ACTIVE", pageable);
        }
        
        return products.map(productMapper::toProductResponseDTO);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new com.beej.exception.ResourceNotFoundException("Product not found with id: " + id));
        
        return productMapper.toProductResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getFeaturedProducts() {
        List<Product> products = productRepository.findTop4ByFeaturedTrue();
        return products.stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public Page<ProductResponseDTO> searchProducts(String query, Pageable pageable) {
        Page<Product> products = productRepository.findByNameContaining(query, pageable);
        return products.map(productMapper::toProductResponseDTO);
    }

    @Override
    public List<ProductResponseDTO> getNewArrivals() {
        List<Product> products = productRepository.findTop8ByStatusOrderByCreatedAtDesc("ACTIVE");
        return products.stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public List<ProductResponseDTO> getTrendingProducts() {
        // For now, return featured products as trending
        // In a real application, this would be based on sales data, views, etc.
        List<Product> products = productRepository.findTop4ByFeaturedTrue();
        return products.stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }
}
