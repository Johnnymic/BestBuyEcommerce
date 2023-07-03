package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.domain.entity.CartItems;
import com.bestbuy.ecommerce.dto.CartItemsResponse;

public interface CartService {
    String addItemsToCart(Long productId);
}
