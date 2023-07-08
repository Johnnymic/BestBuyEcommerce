package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService  customerService;

    @GetMapping("/add-product-to-favorite/{productId}")
    public ResponseEntity<ApiResponse<String>>userAddProductToFavourite(@PathVariable("productId") Long productId){
        ApiResponse<String> apiResponse = new ApiResponse<>(customerService.userAddProductToFavourite(productId));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
