package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.OrderRequest;
import com.bestbuy.ecommerce.dto.responses.AdminOrderResponse;
import com.bestbuy.ecommerce.dto.responses.OrderResponse;
import com.bestbuy.ecommerce.enums.DeliveryStatus;
import com.bestbuy.ecommerce.enums.PickupStatus;
import org.springframework.data.domain.Page;

public interface OrderService {
    String saveOrder(OrderRequest orderRequest);

    Page<AdminOrderResponse> viewOrderByPaginated(Integer pageNo, Integer pageSize, String sortBy);

    Page<OrderResponse> viewHistory(Integer pageNo, Integer pageSize);

    OrderResponse viewParticularOrder(Long orderId);

    Page<OrderResponse> viewOrderByDeliveryStatus(DeliveryStatus status, Integer pageNo, Integer pageSize);

    Page<OrderResponse>  viewOrderPickupStatus(PickupStatus pickupStatus, Integer pageNo, Integer pageSize);
}
