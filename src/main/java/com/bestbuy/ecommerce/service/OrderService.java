package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.OrderRequest;
import com.bestbuy.ecommerce.dto.responses.OrderResponse;

public interface OrderService {
    String saveOrder(OrderRequest orderRequest);
}
