package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.ReviewRequest;
import com.bestbuy.ecommerce.dto.responses.ReviewResponse;
import org.springframework.data.domain.Page;

public interface ReviewService {
    ReviewResponse addReviewToView(ReviewRequest reviewRequest);

    ReviewResponse updateReviewForProduct(Long reviewId, ReviewRequest reviewRequest);

    String deleteReview(Long reviewId);

    Page<ReviewResponse> viewAllReview(Integer pageNo, Integer pageSize, String sortBy);
    Page<ReviewResponse> viewAllReviewByProduct(Long productId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);
}

