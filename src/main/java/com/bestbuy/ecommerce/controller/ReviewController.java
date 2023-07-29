package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.ReviewRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.ReviewResponse;
import com.bestbuy.ecommerce.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;



    @PostMapping("/add/new/review/product")
    public ResponseEntity<ApiResponse<ReviewResponse>> addReviewToProduct(@RequestBody ReviewRequest reviewRequest){
        ApiResponse<ReviewResponse>  apiResponse = new ApiResponse<>(reviewService.addReviewToView(reviewRequest));

        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/review/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateProductReview(@PathVariable("reviewId") Long reviewId,@RequestBody ReviewRequest reviewRequest){
        ApiResponse<ReviewResponse>  apiResponse = new ApiResponse<>(reviewService.updateReviewForProduct( reviewId,reviewRequest));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
    @DeleteMapping("/deleted/review/{reviewId}")
    public ResponseEntity<ApiResponse<String>>deleteReview(@PathVariable("reviewId") Long reviewId){
        ApiResponse<String>  apiResponse = new ApiResponse<>(reviewService.deleteReview( reviewId));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/view/all/review/")
    public ResponseEntity<ApiResponse<Page<ReviewResponse>>>viewAllReviews(@RequestParam(defaultValue = "0")Integer pageNo,
                                                                           @RequestParam(defaultValue = "0")Integer pageSize,
                                                                           @RequestParam(defaultValue = "Id")String  sortBy

                                                                           ){
        ApiResponse <Page<ReviewResponse>>  apiResponse = new ApiResponse<>(reviewService.viewAllReview(pageNo,pageSize,sortBy));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
    @GetMapping("/view/all/review/{productId}")
    public ResponseEntity<ApiResponse<Page<ReviewResponse>>>viewAllReviewsByProduct(
                                                                           @PathVariable("productId") Long productId,
                                                                           @RequestParam(defaultValue = "0")Integer pageNo,
                                                                           @RequestParam(defaultValue = "0")Integer pageSize,
                                                                            @RequestParam(defaultValue = "Id")String  sortBy,
                                                                           @RequestParam(defaultValue = "true") boolean isAscending
    ){
        ApiResponse <Page<ReviewResponse>>  apiResponse = new ApiResponse<>(reviewService.viewAllReviewByProduct(productId,pageNo,pageSize,sortBy,isAscending));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);


    }


}
