package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.domain.entity.Cart;
import com.bestbuy.ecommerce.domain.entity.CartItems;
import com.bestbuy.ecommerce.dto.request.CartRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.CartResponse;
import com.bestbuy.ecommerce.service.CartService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private  final CartService cartService;

    @PostMapping("/items/add-to-cart")
    public ResponseEntity<ApiResponse <String>>addItemToCArt( @RequestBody CartRequest cartRequest){
     ApiResponse<String> response = new ApiResponse<>(cartService.addItemsToCart(cartRequest));
     return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/delete-cart/{cartItemsId}")
    public ResponseEntity<ApiResponse<String>>deleteItemsFromCart( @PathVariable("cartItemsId")  Long items){
    ApiResponse<String> response = new ApiResponse<>(cartService.deleteItemsFromCart(items));
    return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping ("/add-to-quantity/{productId}")
    public ResponseEntity<ApiResponse<String>>addItemsToQuantity(@PathVariable("productId") Long productId){
        ApiResponse<String> response = new ApiResponse<>(cartService.addItemsToQuantity(productId));
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/reduce-quantity/{productId}")
    public ResponseEntity<ApiResponse<String >>reduceItemQuantity(@PathVariable("productId") Long productId){
        ApiResponse<String> response = new ApiResponse<>(cartService.reduceItemQuantity(productId));
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/clear-cart/")
    public ResponseEntity<ApiResponse<String >>clearCart(){
        ApiResponse<String> response = new ApiResponse<>(cartService.clearCart());
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/view-cart")
    public ResponseEntity<ApiResponse<CartResponse>>viewCartByAppUser(){
        ApiResponse<CartResponse> response = new ApiResponse<>(cartService.viewCartByUser());
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

}
