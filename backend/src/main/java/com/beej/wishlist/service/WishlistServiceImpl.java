package com.beej.wishlist.service;

import com.beej.wishlist.dto.*;
import com.beej.wishlist.entity.*;
import com.beej.wishlist.mapper.*;
import com.beej.wishlist.repository.*;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistMapper wishlistMapper;
    private final WishlistItemMapper wishlistItemMapper;

    @Override
    @Transactional
    public WishlistDTO createWishlist(CreateWishlistRequestDTO request) {
        log.info("Creating wishlist for user: {}", request.getUserId());
        
        Wishlist wishlist = Wishlist.builder()
                .userId(request.getUserId())
                .name(request.getName())
                .description(request.getDescription())
                .isDefault(request.getIsDefault())
                .isPrivate(request.getIsPrivate())
                .notifyOnPriceDrop(request.getNotifyOnPriceDrop())
                .notifyOnBackInStock(request.getNotifyOnBackInStock())
                .itemCount(0)
                .totalValue(BigDecimal.ZERO)
                .build();
        
        if (request.getIsDefault()) {
            wishlistRepository.unsetOtherDefaultWishlists(request.getUserId(), null);
        }
        
        Wishlist saved = wishlistRepository.save(wishlist);
        log.info("Created wishlist with ID: {}", saved.getId());
        return wishlistMapper.toWishlistDTO(saved);
    }

    @Override
    public WishlistDTO getWishlistById(Long id) {
        log.info("Fetching wishlist with ID: {}", id);
        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + id));
        
        wishlist.setLastViewedAt(LocalDateTime.now());
        wishlistRepository.save(wishlist);
        
        return wishlistMapper.toWishlistDTO(wishlist);
    }

    @Override
    public WishlistDTO getWishlistByShareToken(String shareToken) {
        log.info("Fetching wishlist with share token: {}", shareToken);
        Wishlist wishlist = wishlistRepository.findByShareToken(shareToken)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with share token: " + shareToken));
        
        if (wishlist.getIsPrivate()) {
            throw new ResourceNotFoundException("This wishlist is private");
        }
        
        return wishlistMapper.toWishlistDTO(wishlist);
    }

    @Override
    public List<WishlistDTO> getUserWishlists(Long userId) {
        log.info("Fetching wishlists for user: {}", userId);
        return wishlistRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(wishlistMapper::toWishlistDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WishlistDTO> getUserPublicWishlists(Long userId) {
        log.info("Fetching public wishlists for user: {}", userId);
        return wishlistRepository.findByUserIdAndIsPrivateFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(wishlistMapper::toWishlistDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WishlistDTO getDefaultWishlist(Long userId) {
        log.info("Fetching default wishlist for user: {}", userId);
        return wishlistRepository.findByUserIdAndIsDefaultTrue(userId)
                .map(wishlistMapper::toWishlistDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public WishlistDTO updateWishlist(Long id, UpdateWishlistRequestDTO request) {
        log.info("Updating wishlist with ID: {}", id);
        
        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + id));
        
        if (request.getName() != null) wishlist.setName(request.getName());
        if (request.getDescription() != null) wishlist.setDescription(request.getDescription());
        if (request.getIsDefault() != null) wishlist.setIsDefault(request.getIsDefault());
        if (request.getIsPrivate() != null) wishlist.setIsPrivate(request.getIsPrivate());
        if (request.getNotifyOnPriceDrop() != null) wishlist.setNotifyOnPriceDrop(request.getNotifyOnPriceDrop());
        if (request.getNotifyOnBackInStock() != null) wishlist.setNotifyOnBackInStock(request.getNotifyOnBackInStock());
        
        if (request.getIsDefault() != null && request.getIsDefault()) {
            wishlistRepository.unsetOtherDefaultWishlists(wishlist.getUserId(), id);
        }
        
        Wishlist saved = wishlistRepository.save(wishlist);
        return wishlistMapper.toWishlistDTO(saved);
    }

    @Override
    @Transactional
    public WishlistDTO setDefaultWishlist(Long userId, Long wishlistId) {
        log.info("Setting wishlist {} as default for user: {}", wishlistId, userId);
        
        wishlistRepository.unsetOtherDefaultWishlists(userId, wishlistId);
        
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));
        
        wishlist.setIsDefault(true);
        Wishlist saved = wishlistRepository.save(wishlist);
        return wishlistMapper.toWishlistDTO(saved);
    }

    @Override
    @Transactional
    public void deleteWishlist(Long id) {
        log.info("Deleting wishlist with ID: {}", id);
        
        if (!wishlistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Wishlist not found with id: " + id);
        }
        
        wishlistItemRepository.deleteByWishlistId(id);
        wishlistRepository.deleteById(id);
    }

    @Override
    @Transactional
    public WishlistDTO addItemToWishlist(AddToWishlistRequestDTO request) {
        log.info("Adding item to wishlist: {}", request.getWishlistId());
        
        Wishlist wishlist = wishlistRepository.findById(request.getWishlistId())
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + request.getWishlistId()));
        
        wishlistItemRepository.findByWishlistIdAndProductId(request.getWishlistId(), request.getProductId())
                .ifPresent(existing -> {
                    throw new IllegalStateException("Product already exists in this wishlist");
                });
        
        BigDecimal totalPrice = request.getUnitPrice() != null 
                ? request.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity()))
                : BigDecimal.ZERO;
        
        WishlistItem item = WishlistItem.builder()
                .wishlist(wishlist)
                .productId(request.getProductId())
                .productName(request.getProductName())
                .productSku(request.getProductSku())
                .productImage(request.getProductImage())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .totalPrice(totalPrice)
                .addedPrice(request.getUnitPrice())
                .priority(request.getPriority())
                .notes(request.getNotes())
                .isAvailable(true)
                .isNotified(false)
                .build();
        
        wishlistItemRepository.save(item);
        
        updateWishlistTotals(request.getWishlistId());
        
        return getWishlistById(request.getWishlistId());
    }

    @Override
    @Transactional
    public WishlistDTO removeItemFromWishlist(Long wishlistId, Long itemId) {
        log.info("Removing item {} from wishlist {}", itemId, wishlistId);
        
        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found with id: " + itemId));
        
        if (!item.getWishlist().getId().equals(wishlistId)) {
            throw new IllegalStateException("Item does not belong to this wishlist");
        }
        
        wishlistItemRepository.delete(item);
        
        updateWishlistTotals(wishlistId);
        
        return getWishlistById(wishlistId);
    }

    @Override
    @Transactional
    public WishlistDTO updateWishlistItem(Long itemId, UpdateWishlistItemRequestDTO request) {
        log.info("Updating wishlist item: {}", itemId);
        
        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found with id: " + itemId));
        
        if (request.getQuantity() != null) item.setQuantity(request.getQuantity());
        if (request.getPriority() != null) item.setPriority(request.getPriority());
        if (request.getNotes() != null) item.setNotes(request.getNotes());
        if (request.getUnitPrice() != null) {
            item.setUnitPrice(request.getUnitPrice());
            item.setTotalPrice(request.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        
        wishlistItemRepository.save(item);
        
        Long wishlistId = item.getWishlist().getId();
        updateWishlistTotals(wishlistId);
        
        return getWishlistById(wishlistId);
    }

    @Override
    @Transactional
    public WishlistDTO moveItemToAnotherWishlist(MoveWishlistItemRequestDTO request) {
        log.info("Moving item {} from wishlist {} to wishlist {}", 
                request.getItemId(), request.getSourceWishlistId(), request.getTargetWishlistId());
        
        WishlistItem item = wishlistItemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found with id: " + request.getItemId()));
        
        Wishlist targetWishlist = wishlistRepository.findById(request.getTargetWishlistId())
                .orElseThrow(() -> new ResourceNotFoundException("Target wishlist not found with id: " + request.getTargetWishlistId()));
        
        wishlistItemRepository.findByWishlistIdAndProductId(request.getTargetWishlistId(), item.getProductId())
                .ifPresent(existing -> {
                    throw new IllegalStateException("Product already exists in target wishlist");
                });
        
        item.setWishlist(targetWishlist);
        wishlistItemRepository.save(item);
        
        updateWishlistTotals(request.getSourceWishlistId());
        updateWishlistTotals(request.getTargetWishlistId());
        
        return getWishlistById(request.getTargetWishlistId());
    }

    @Override
    public List<WishlistItemDTO> getWishlistItems(Long wishlistId) {
        log.info("Fetching items for wishlist: {}", wishlistId);
        return wishlistItemRepository.findByWishlistIdOrderByCreatedAtDesc(wishlistId)
                .stream()
                .map(wishlistItemMapper::toWishlistItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WishlistItemDTO> getItemsByPriority(Long wishlistId, String priority) {
        log.info("Fetching items with priority {} from wishlist: {}", priority, wishlistId);
        return wishlistItemRepository.findByWishlistIdOrderByCreatedAtDesc(wishlistId)
                .stream()
                .filter(item -> item.getPriority().equals(priority))
                .map(wishlistItemMapper::toWishlistItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WishlistDTO updateItemQuantity(Long itemId, Integer quantity) {
        log.info("Updating quantity for item: {} to {}", itemId, quantity);
        
        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found with id: " + itemId));
        
        item.setQuantity(quantity);
        item.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
        
        wishlistItemRepository.save(item);
        
        Long wishlistId = item.getWishlist().getId();
        updateWishlistTotals(wishlistId);
        
        return getWishlistById(wishlistId);
    }

    @Override
    @Transactional
    public WishlistDTO updateItemPriority(Long itemId, String priority) {
        log.info("Updating priority for item: {} to {}", itemId, priority);
        
        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found with id: " + itemId));
        
        item.setPriority(priority);
        wishlistItemRepository.save(item);
        
        return getWishlistById(item.getWishlist().getId());
    }

    @Override
    public List<WishlistItemDTO> getPriceDroppedItems(Long userId) {
        log.info("Fetching price dropped items for user: {}", userId);
        return wishlistItemRepository.findPriceDroppedItems()
                .stream()
                .filter(item -> item.getWishlist().getUserId().equals(userId))
                .map(wishlistItemMapper::toWishlistItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WishlistItemDTO> getUnavailableItemsToNotify(Long userId) {
        log.info("Fetching unavailable items to notify for user: {}", userId);
        return wishlistItemRepository.findUnavailableItemsToNotify()
                .stream()
                .filter(item -> item.getWishlist().getUserId().equals(userId))
                .map(wishlistItemMapper::toWishlistItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WishlistDTO markItemAsNotified(Long itemId) {
        log.info("Marking item {} as notified", itemId);
        
        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found with id: " + itemId));
        
        item.setIsNotified(true);
        item.setNotifiedAt(LocalDateTime.now());
        wishlistItemRepository.save(item);
        
        return getWishlistById(item.getWishlist().getId());
    }

    @Override
    public Long countUserWishlists(Long userId) {
        return wishlistRepository.countByUserId(userId);
    }

    @Override
    public Long countWishlistItems(Long wishlistId) {
        return wishlistItemRepository.countByWishlistId(wishlistId);
    }

    @Override
    public Long countUserWishlistItems(Long userId) {
        return wishlistItemRepository.countByUserId(userId);
    }

    @Override
    public BigDecimal getWishlistTotalValue(Long wishlistId) {
        BigDecimal total = wishlistItemRepository.sumTotalValueByWishlistId(wishlistId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public List<Object[]> getMostWishedProducts() {
        return wishlistItemRepository.findMostWishedProducts();
    }

    @Override
    public List<Long> getUserWishlistedProductIds(Long userId) {
        return wishlistItemRepository.findDistinctProductIdsByUserId(userId);
    }

    @Override
    public Boolean isProductInUserWishlist(Long userId, Long productId) {
        return !wishlistItemRepository.findByUserIdAndProductId(userId, productId).isEmpty();
    }

    @Override
    @Transactional
    public WishlistDTO addProductToDefaultWishlist(Long userId, Long productId, String productName, 
                                                      String productSku, String productImage, 
                                                      BigDecimal unitPrice) {
        log.info("Adding product {} to default wishlist for user: {}", productId, userId);
        
        Wishlist defaultWishlist = wishlistRepository.findByUserIdAndIsDefaultTrue(userId)
                .orElseGet(() -> {
                    Wishlist newDefault = Wishlist.builder()
                            .userId(userId)
                            .name("My Wishlist")
                            .description("Default wishlist")
                            .isDefault(true)
                            .isPrivate(true)
                            .itemCount(0)
                            .totalValue(BigDecimal.ZERO)
                            .notifyOnPriceDrop(true)
                            .notifyOnBackInStock(true)
                            .build();
                    return wishlistRepository.save(newDefault);
                });
        
        wishlistItemRepository.findByWishlistIdAndProductId(defaultWishlist.getId(), productId)
                .ifPresent(existing -> {
                    throw new IllegalStateException("Product already exists in default wishlist");
                });
        
        WishlistItem item = WishlistItem.builder()
                .wishlist(defaultWishlist)
                .productId(productId)
                .productName(productName)
                .productSku(productSku)
                .productImage(productImage)
                .quantity(1)
                .unitPrice(unitPrice)
                .totalPrice(unitPrice)
                .addedPrice(unitPrice)
                .priority("NORMAL")
                .isAvailable(true)
                .isNotified(false)
                .build();
        
        wishlistItemRepository.save(item);
        
        updateWishlistTotals(defaultWishlist.getId());
        
        return getWishlistById(defaultWishlist.getId());
    }

    @Override
    @Transactional
    public void clearWishlist(Long wishlistId) {
        log.info("Clearing wishlist: {}", wishlistId);
        
        wishlistItemRepository.deleteByWishlistId(wishlistId);
        
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));
        
        wishlist.setItemCount(0);
        wishlist.setTotalValue(BigDecimal.ZERO);
        wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public String generateShareToken(Long wishlistId) {
        log.info("Generating share token for wishlist: {}", wishlistId);
        
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));
        
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        wishlist.setShareToken(token);
        wishlist.setIsPrivate(false);
        wishlistRepository.save(wishlist);
        
        return token;
    }

    @Override
    @Transactional
    public WishlistDTO revokeShareToken(Long wishlistId) {
        log.info("Revoking share token for wishlist: {}", wishlistId);
        
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));
        
        wishlist.setShareToken(null);
        wishlist.setIsPrivate(true);
        Wishlist saved = wishlistRepository.save(wishlist);
        
        return wishlistMapper.toWishlistDTO(saved);
    }

    @Override
    @Transactional
    public void updateWishlistTotals(Long wishlistId) {
        log.info("Updating totals for wishlist: {}", wishlistId);
        
        Long itemCount = wishlistItemRepository.countByWishlistId(wishlistId);
        BigDecimal totalValue = wishlistItemRepository.sumTotalValueByWishlistId(wishlistId);
        
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));
        
        wishlist.setItemCount(itemCount.intValue());
        wishlist.setTotalValue(totalValue != null ? totalValue : BigDecimal.ZERO);
        wishlistRepository.save(wishlist);
    }
}
