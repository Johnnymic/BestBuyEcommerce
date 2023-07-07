package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.CartRequest;
import com.bestbuy.ecommerce.dto.responses.CartResponse;

import java.util.List;

public interface CartService {
    String addItemsToCart(CartRequest cartRequest);

    String  deleteItemsFromCart(Long productId);

    String  addItemsToQuantity(Long productId);


    String reduceItemQuantity(Long productId);

    String  clearCart();

    CartResponse viewCartByUser();
}
