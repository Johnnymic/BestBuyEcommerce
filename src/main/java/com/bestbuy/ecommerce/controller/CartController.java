package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.domain.entity.CartItems;
import com.bestbuy.ecommerce.dto.CartItemsResponse;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.CartResponse;
import com.bestbuy.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private  final CartService cartService;

    @PostMapping("/items{productId}")
    public ResponseEntity<ApiResponse <String>>addItemToCArt(@PathVariable("productId")  Long productId){
     ApiResponse<String> response = new ApiResponse<>(cartService.addItemsToCart(productId));
     return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
