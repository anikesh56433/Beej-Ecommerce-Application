package com.beej.review.service;

import com.beej.review.dto.*;
import com.beej.review.entity.*;
import com.beej.review.mapper.*;
import com.beej.review.repository.*;
import com.beej.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewHelpfulRepository reviewHelpfulRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewImageMapper reviewImageMapper;
    private final ReviewHelpfulMapper reviewHelpfulMapper;

    @Override
    @Transactional
    public ReviewDTO createReview(CreateReviewRequestDTO request) {
        log.info("Creating review for product: {} by user: {}", request.getProductId(), request.getUserId());
        
        Review review = Review.builder()
                .productId(request.getProductId())
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .rating(request.getRating())
                .title(request.getTitle())
                .content(request.getContent())
                .isVerified(false)
                .isApproved(false)
                .isFeatured(false)
                .helpfulCount(0)
                .notHelpfulCount(0)
                .reviewerIp(request.getReviewerIp())
                .reviewerUserAgent(request.getReviewerUserAgent())
                .isDeleted(false)
                .build();
        
        Review saved = reviewRepository.save(review);
        
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            List<ReviewImage> images = request.getImageUrls().stream()
                    .map(url -> ReviewImage.builder()
                            .review(saved)
                            .imageUrl(url)
                            .imageOrder(0)
                            .isPrimary(false)
                            .build())
                    .collect(Collectors.toList());
            
            if (!images.isEmpty()) {
                images.get(0).setIsPrimary(true);
                for (int i = 0; i < images.size(); i++) {
                    images.get(i).setImageOrder(i);
                }
                reviewImageRepository.saveAll(images);
            }
        }
        
        log.info("Created review with ID: {}", saved.getId());
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        log.info("Fetching review with ID: {}", id);
        return reviewRepository.findById(id)
                .map(reviewMapper::toReviewDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
    }

    @Override
    public List<ReviewDTO> getProductReviews(Long productId) {
        log.info("Fetching reviews for product: {}", productId);
        return reviewRepository.findByProductIdAndIsApprovedTrueOrderByCreatedAtDesc(productId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getProductReviews(Long productId, int limit) {
        log.info("Fetching {} reviews for product: {}", limit, productId);
        return reviewRepository.findByProductIdAndIsApprovedTrueOrderByCreatedAtDesc(productId, 
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getProductReviewsByRating(Long productId, Integer minRating, Integer maxRating) {
        log.info("Fetching reviews for product: {} with rating between {} and {}", productId, minRating, maxRating);
        return reviewRepository.findByProductIdAndIsApprovedTrueAndRatingBetweenOrderByCreatedAtDesc(productId, minRating, maxRating, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getUserReviews(Long userId) {
        log.info("Fetching reviews for user: {}", userId);
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getOrderReviews(Long orderId) {
        log.info("Fetching reviews for order: {}", orderId);
        return reviewRepository.findByOrderIdOrderByCreatedAtDesc(orderId, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getApprovedReviews() {
        log.info("Fetching all approved reviews");
        return reviewRepository.findByIsApprovedTrueOrderByCreatedAtDesc(
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getFeaturedReviews() {
        log.info("Fetching featured reviews");
        return reviewRepository.findByIsApprovedTrueAndIsFeaturedTrueOrderByCreatedAtDesc(
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getPendingReviews() {
        log.info("Fetching pending reviews");
        return reviewRepository.findByIsApprovedFalseOrderByCreatedAtDesc(
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> filterReviews(ReviewFilterRequestDTO filter) {
        log.info("Filtering reviews with criteria: {}", filter);
        
        Sort.Direction direction = filter.getSortOrder().equalsIgnoreCase("desc") 
                ? Sort.Direction.DESC 
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit(), 
                Sort.by(direction, filter.getSortBy()));
        
        Page<Review> reviews;
        
        if (filter.getProductId() != null) {
            if (filter.getMinRating() != null && filter.getMaxRating() != null) {
                reviews = reviewRepository.findByProductIdAndIsApprovedTrueAndRatingBetweenOrderByCreatedAtDesc(
                        filter.getProductId(), filter.getMinRating(), filter.getMaxRating(), pageable);
            } else {
                reviews = reviewRepository.findByProductIdAndIsApprovedTrueOrderByCreatedAtDesc(
                        filter.getProductId(), pageable);
            }
        } else if (filter.getUserId() != null) {
            reviews = reviewRepository.findByUserIdOrderByCreatedAtDesc(filter.getUserId(), pageable);
        } else if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            reviews = reviewRepository.searchApprovedReviews(filter.getSearch(), pageable);
        } else {
            reviews = reviewRepository.findByIsApprovedTrueOrderByCreatedAtDesc(pageable);
        }
        
        return reviews.getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> searchReviews(String search) {
        log.info("Searching reviews with query: {}", search);
        return reviewRepository.searchApprovedReviews(search, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> searchProductReviews(Long productId, String search) {
        log.info("Searching reviews for product {} with query: {}", productId, search);
        return reviewRepository.searchProductReviews(productId, search, 
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent()
                .stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewDTO updateReview(Long id, UpdateReviewRequestDTO request) {
        log.info("Updating review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        if (request.getCustomerName() != null) {
            review.setCustomerName(request.getCustomerName());
        }
        if (request.getCustomerEmail() != null) {
            review.setCustomerEmail(request.getCustomerEmail());
        }
        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }
        if (request.getTitle() != null) {
            review.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            review.setContent(request.getContent());
        }
        if (request.getIsVerified() != null) {
            review.setIsVerified(request.getIsVerified());
        }
        if (request.getIsApproved() != null) {
            review.setIsApproved(request.getIsApproved());
        }
        if (request.getIsFeatured() != null) {
            review.setIsFeatured(request.getIsFeatured());
        }
        if (request.getAdminResponse() != null) {
            review.setAdminResponse(request.getAdminResponse());
            review.setAdminRespondedAt(LocalDateTime.now());
        }
        
        Review saved = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    @Transactional
    public ReviewDTO approveReview(Long id) {
        log.info("Approving review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setIsApproved(true);
        Review saved = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    @Transactional
    public ReviewDTO rejectReview(Long id) {
        log.info("Rejecting review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setIsApproved(false);
        Review saved = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    @Transactional
    public ReviewDTO featureReview(Long id) {
        log.info("Featuring review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setIsFeatured(true);
        Review saved = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    @Transactional
    public ReviewDTO unfeatureReview(Long id) {
        log.info("Unfeaturing review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setIsFeatured(false);
        Review saved = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    @Transactional
    public ReviewDTO verifyReview(Long id) {
        log.info("Verifying review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setIsVerified(true);
        Review saved = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    @Transactional
    public ReviewDTO addAdminResponse(Long id, AdminReviewResponseDTO response) {
        log.info("Adding admin response to review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setAdminResponse(response.getAdminResponse());
        review.setAdminRespondedBy(response.getAdminRespondedBy());
        review.setAdminRespondedById(response.getAdminRespondedById());
        review.setAdminRespondedAt(LocalDateTime.now());
        
        Review saved = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    @Transactional
    public ReviewDTO markAsHelpful(ReviewHelpfulRequestDTO request) {
        log.info("Marking review {} as helpful by user {}", request.getReviewId(), request.getUserId());
        
        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + request.getReviewId()));
        
        reviewHelpfulRepository.findByReviewIdAndUserId(request.getReviewId(), request.getUserId())
                .ifPresentOrElse(
                        existing -> {
                            if (!existing.getIsHelpful()) {
                                review.setHelpfulCount(review.getHelpfulCount() + 1);
                                review.setNotHelpfulCount(review.getNotHelpfulCount() - 1);
                                existing.setIsHelpful(true);
                                reviewHelpfulRepository.save(existing);
                            }
                        },
                        () -> {
                            ReviewHelpful helpful = reviewHelpfulMapper.toEntity(
                                    review, request.getUserId(), true, 
                                    request.getIpAddress(), request.getUserAgent());
                            reviewHelpfulRepository.save(helpful);
                            review.setHelpfulCount(review.getHelpfulCount() + 1);
                        }
                );
        
        Review saved = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    @Transactional
    public ReviewDTO markAsNotHelpful(ReviewHelpfulRequestDTO request) {
        log.info("Marking review {} as not helpful by user {}", request.getReviewId(), request.getUserId());
        
        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + request.getReviewId()));
        
        reviewHelpfulRepository.findByReviewIdAndUserId(request.getReviewId(), request.getUserId())
                .ifPresentOrElse(
                        existing -> {
                            if (existing.getIsHelpful()) {
                                review.setHelpfulCount(review.getHelpfulCount() - 1);
                                review.setNotHelpfulCount(review.getNotHelpfulCount() + 1);
                                existing.setIsHelpful(false);
                                reviewHelpfulRepository.save(existing);
                            }
                        },
                        () -> {
                            ReviewHelpful helpful = reviewHelpfulMapper.toEntity(
                                    review, request.getUserId(), false, 
                                    request.getIpAddress(), request.getUserAgent());
                            reviewHelpfulRepository.save(helpful);
                            review.setNotHelpfulCount(review.getNotHelpfulCount() + 1);
                        }
                );
        
        Review saved = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(saved);
    }

    @Override
    @Transactional
    public void deleteReview(Long id, String deletedBy, Long deletedById) {
        log.info("Deleting review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setIsDeleted(true);
        review.setDeletedAt(LocalDateTime.now());
        review.setDeletedBy(deletedBy);
        review.setDeletedById(deletedById);
        
        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void softDeleteReview(Long id, String deletedBy, Long deletedById) {
        log.info("Soft deleting review with ID: {}", id);
        deleteReview(id, deletedBy, deletedById);
    }

    @Override
    public Double getAverageRatingByProductId(Long productId) {
        log.info("Getting average rating for product: {}", productId);
        return reviewRepository.getAverageRatingByProductId(productId);
    }

    @Override
    public Long countApprovedReviewsByProductId(Long productId) {
        log.info("Counting approved reviews for product: {}", productId);
        return reviewRepository.countApprovedReviewsByProductId(productId);
    }

    @Override
    public Long countReviewsByProductIdAndRating(Long productId, Integer rating) {
        log.info("Counting reviews for product {} with rating {}", productId, rating);
        return reviewRepository.countReviewsByProductIdAndRating(productId, rating);
    }

    @Override
    public List<Object[]> getRatingDistributionByProductId(Long productId) {
        log.info("Getting rating distribution for product: {}", productId);
        return reviewRepository.getRatingDistributionByProductId(productId);
    }

    @Override
    public Long countPendingReviews() {
        log.info("Counting pending reviews");
        return reviewRepository.countPendingReviews();
    }

    @Override
    public Long countApprovedReviews() {
        log.info("Counting approved reviews");
        return reviewRepository.countApprovedReviews();
    }

    @Override
    public Long countDeletedReviews() {
        log.info("Counting deleted reviews");
        return reviewRepository.countDeletedReviews();
    }

    @Override
    public List<Long> getDistinctProductIds() {
        log.info("Getting distinct product IDs with reviews");
        return reviewRepository.findDistinctProductIds();
    }
}
