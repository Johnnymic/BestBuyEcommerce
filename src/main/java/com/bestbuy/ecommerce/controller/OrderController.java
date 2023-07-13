package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.OrderRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.OrderResponse;
import com.bestbuy.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/customer/save/order")
    public ResponseEntity<ApiResponse<String>> saveOrders(@RequestBody OrderRequest orderRequest){
        ApiResponse<String> apiResponse = new ApiResponse<>(orderService.saveOrder(orderRequest));
        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


}
