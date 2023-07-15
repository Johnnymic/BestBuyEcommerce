package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.OrderRequest;
import com.bestbuy.ecommerce.dto.responses.AdminOrderResponse;
import com.bestbuy.ecommerce.dto.responses.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {
    String saveOrder(OrderRequest orderRequest);

    Page<AdminOrderResponse> viewOrderByPaginated(Integer pageNo, Integer pageSize, String sortBy);

    Page<OrderResponse> viewHistory(Integer pageNo, Integer pageSize);
}
