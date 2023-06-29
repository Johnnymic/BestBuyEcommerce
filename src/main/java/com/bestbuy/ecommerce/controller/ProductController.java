package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;
import com.bestbuy.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private  final ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse<ProductResponse>>addProduct(@RequestBody ProductRequest productRequest){
        ApiResponse<ProductResponse>  response= new ApiResponse<>(productService.addNewProduct(productRequest));
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
