package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.OrderRequest;
import com.bestbuy.ecommerce.dto.responses.AdminOrderResponse;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.OrderResponse;
import com.bestbuy.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/customer/view/orders/paginated")
    public ResponseEntity<ApiResponse<Page<AdminOrderResponse>>>viewOrderByPagination(@RequestParam(defaultValue = "0")Integer pageNo,
                                                                                      @RequestParam(defaultValue = "16") Integer pageSize,
                                                                                      @RequestParam(defaultValue = "id") String sortBy

                                                                                 ){
        ApiResponse<Page<AdminOrderResponse>> apiResponse = new ApiResponse<>(orderService.viewOrderByPaginated(pageNo,pageSize,sortBy));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }

    @GetMapping ("/customer/view/history")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>>viewHistory(@RequestParam(defaultValue = "0")Integer pageNo,
                                                                       @RequestParam(defaultValue = "16") Integer pageSize){
        ApiResponse<Page<OrderResponse>> apiResponse = new ApiResponse<>(orderService.viewHistory(pageNo,pageSize));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping ("/customer/view/particular/order/{orderId}")
    ResponseEntity<ApiResponse<OrderResponse>>viewParticularOrder(@PathVariable("orderId") Long OrderId){
        ApiResponse<>
    }

}
