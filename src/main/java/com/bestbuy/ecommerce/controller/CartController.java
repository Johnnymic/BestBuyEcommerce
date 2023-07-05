package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.CartRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
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

    @PostMapping("/items/add-to-cart")
    public ResponseEntity<ApiResponse <String>>addItemToCArt( @RequestBody CartRequest cartRequest){
     ApiResponse<String> response = new ApiResponse<>(cartService.addItemsToCart(cartRequest));
     return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/delete-cart{cartItemsId}")
    public ResponseEntity<ApiResponse<String>>deleteItemsFromCart( @PathVariable("cartItemsId")  Long items){
    ApiResponse<String> response = new ApiResponse<>(cartService.deleteItemsFromCart(items));
    return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
