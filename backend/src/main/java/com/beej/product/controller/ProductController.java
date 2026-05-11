package com.beej.product.controller;

import com.beej.common.response.ApiResponse;
import com.beej.product.dto.ProductResponseDTO;
import com.beej.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "APIs for managing products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a paginated list of products with optional filters")
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> getAllProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit,
            @Parameter(description = "Category ID filter") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Search term") @RequestParam(required = false) String search,
            @Parameter(description = "Minimum price filter") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortOrder) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sortBy));
        
        Page<ProductResponseDTO> products = productService.getProducts(categoryId, search, minPrice, maxPrice, pageable);
        
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", products));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Product retrieved successfully", product));
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured products", description = "Retrieve a list of featured products")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getFeaturedProducts() {
        List<ProductResponseDTO> products = productService.getFeaturedProducts();
        return ResponseEntity.ok(ApiResponse.success("Featured products retrieved successfully", products));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by name or description")
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> searchProducts(
            @Parameter(description = "Search query") @RequestParam String q,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<ProductResponseDTO> products = productService.searchProducts(q, pageable);
        
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", products));
    }

    @GetMapping("/new-arrivals")
    @Operation(summary = "Get new arrivals", description = "Retrieve recently added products")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getNewArrivals() {
        List<ProductResponseDTO> products = productService.getNewArrivals();
        return ResponseEntity.ok(ApiResponse.success("New arrivals retrieved successfully", products));
    }

    @GetMapping("/trending")
    @Operation(summary = "Get trending products", description = "Retrieve trending/popular products")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getTrendingProducts() {
        List<ProductResponseDTO> products = productService.getTrendingProducts();
        return ResponseEntity.ok(ApiResponse.success("Trending products retrieved successfully", products));
    }
}
