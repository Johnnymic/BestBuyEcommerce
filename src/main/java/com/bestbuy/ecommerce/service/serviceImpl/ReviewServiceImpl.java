package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.entity.Reviews;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.domain.repository.ReviewRepository;
import com.bestbuy.ecommerce.dto.request.ReviewRequest;
import com.bestbuy.ecommerce.dto.responses.ReviewResponse;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.OrderNotFoundException;
import com.bestbuy.ecommerce.exceptions.ProductNotFoundException;
import com.bestbuy.ecommerce.exceptions.ReviewNotFoundException;
import com.bestbuy.ecommerce.service.AppUserService;
import com.bestbuy.ecommerce.service.OrderService;
import com.bestbuy.ecommerce.service.ReviewService;
import com.bestbuy.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private  final ProductRepository productRepository;

    private  final OrderService orderService;

    private  final AppUserRepository appUserRepository;


    @Override
    public ReviewResponse addReviewToView(ReviewRequest reviewRequest) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("user not login in"));
        if (!hasUserPurchasedProduct(loginUser.getId(), reviewRequest.getProductId())){
            throw new OrderNotFoundException("You must have ordered the product to review it.");
        }

        Product product = productRepository.findById(reviewRequest.getProductId())
                .orElseThrow(()->new ProductNotFoundException("product not found"));

        Reviews review  = mapToRequestEntity(reviewRequest,product,loginUser);

        Reviews reviews = reviewRepository.save(review);
        return mapToReviewResponse(reviews,loginUser,product);
    }

    @Override
    public ReviewResponse updateReviewForProduct(Long reviewId, ReviewRequest reviewRequest) {
        Reviews review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ReviewNotFoundException("Review not found "));
        // Update the review based on the reviewRequest
        review.setTitle(reviewRequest.getTitle());
        review.setComment(reviewRequest.getComment());
        review.setRating(reviewRequest.getRating());
        reviewRepository.save(review);
        return  ReviewResponse.builder()
                .comment(reviewRequest.getComment())
                .title(reviewRequest.getTitle())
                .rating(reviewRequest.getRating())
                .build();
    }

    @Override
    public String deleteReview(Long reviewId) {
        Reviews reviews =reviewRepository.findById(reviewId)
                .orElseThrow(()->new ReviewNotFoundException("Review not found "));
         reviewRepository.delete(reviews);
        return "review has been successfully deleted";
    }

    @Override
    public Page<ReviewResponse> viewAllReview(Integer pageNo, Integer pageSize, String sortBy) {
        List<Reviews> reviewsList = reviewRepository.findAll();
        List<ReviewResponse> responseList = reviewsList.stream()
                .map(reviews -> ReviewResponse.builder()
                        .comment(reviews.getComment())
                        .title(reviews.getTitle())
                        .rating(reviews.getRating())
                        .build()
                ).collect(Collectors.toList());
        Collections.sort(responseList, Comparator.comparing(ReviewResponse::getCreatedAt,Collections.reverseOrder()));
        int min = pageNo*pageSize;
        int max =Math.min(pageSize*(pageNo+1),responseList.size());
        PageRequest pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC,sortBy);
        return new PageImpl<>(responseList.subList(min,max),pageable, responseList.size());
    }

    @Override
    public Page<ReviewResponse> viewAllReviewByProduct(Long productId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        List<Reviews> reviewsList = reviewRepository.findReviewsByProduct(productId);
        List<ReviewResponse> reviewResponseList = reviewsList.stream()
                .map(reviews -> ReviewResponse.builder()
                        .comment(reviews.getComment())
                        .title(reviews.getTitle())
                        .rating(reviews.getRating())
                        .build()).collect(Collectors.toList());
        Collections.sort(reviewResponseList, Comparator.comparing(ReviewResponse::getCreatedAt,Collections.reverseOrder()));
        int min = pageNo*pageSize;
        int max =Math.min(pageSize*(pageNo+1),reviewResponseList.size());
        PageRequest pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC,sortBy);
        return  new PageImpl<>(reviewResponseList.subList(min,max),pageable, reviewResponseList.size());
    }

    private ReviewResponse mapToReviewResponse(Reviews reviews, AppUser loginUser, Product product) {
        return ReviewResponse
                .builder()
                .comment(reviews.getComment())
                .user(AppUser.builder().firstName(loginUser.getLastName()).lastName(loginUser.getLastName()).build())
                .product(Product.builder().productName(product.getProductName()).build())
                .build();
    }


    private Reviews mapToRequestEntity(ReviewRequest reviewRequest, Product product, AppUser loginUser) {
        return  Reviews.builder()
                .comment(reviewRequest.getComment())
                .title(reviewRequest.getTitle())
                .rating(reviewRequest.getRating())
                .product(Product.builder().productName(product.getProductName()).build())
                .user(AppUser.builder().firstName(loginUser.getFirstName()).lastName(loginUser.getLastName()).build())
                .build();
    }

    private boolean hasUserPurchasedProduct(Long id, Long productId) {
      return   orderService.hasUserOrderedProduct(id,productId);
    }
}
