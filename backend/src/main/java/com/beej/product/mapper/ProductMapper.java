package com.beej.product.mapper;

import com.beej.product.dto.ProductResponseDTO;
import com.beej.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductResponseDTO toProductResponseDTO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .shortDescription(product.getShortDescription())
                .sku(product.getSku())
                .price(product.getPrice())
                .comparePrice(product.getComparePrice())
                .status(product.getStatus())
                .featured(product.getFeatured())
                .category(product.getCategory() != null ? 
                    ProductResponseDTO.CategoryDTO.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .slug(product.getCategory().getSlug())
                        .imageUrl(product.getCategory().getImageUrl())
                        .build() : null)
                .brand(product.getBrand())
                .images(product.getImages() != null ? 
                    product.getImages().stream()
                        .map(img -> img.getImageUrl())
                        .toList() : List.of())
                .stockQuantity(product.getInventory() != null ? 
                    product.getInventory().getQuantity() : 0)
                .availableQuantity(product.getInventory() != null ? 
                    product.getInventory().getAvailableQuantity() : 0)
                .averageRating(0.0) // TODO: Calculate from reviews
                .reviewCount(0) // TODO: Count from reviews
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
