package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;
import com.bestbuy.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ProductController {

    private  final ProductService productService;

    @PostMapping("add-product")
    public ResponseEntity<ProductResponse>addProduct( @RequestBody ProductRequest productRequest){

        ProductResponse response = productService.addNewProduct(productRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
