package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;
import com.bestbuy.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private  final ProductService productService;




    @GetMapping("/view/products/{categoryId}/category")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>getAllProductsByCategory(@PathVariable("categoryId") Long categoryId){
        ApiResponse<List<ProductResponse>> response = new ApiResponse<>(productService.findAllProductByCategory(categoryId));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }




    @GetMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<ApiResponse<ProductResponse>>getProductByCategoryId(@PathVariable("productId") Long productId
            ,@PathVariable("categoryId") Long categoryId){
         ApiResponse<ProductResponse> response = new ApiResponse<>(productService.getProductByCategoryId(productId,categoryId));
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }







}
