package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.CartRequest;

public interface CartService {
    String addItemsToCart(CartRequest cartRequest);

    String  deleteItemsFromCart(Long productId);

    String  addItemsToQuantity(Long productId);


    String reduceItemQuantity(Long productId);

    String  clearCart();
}
