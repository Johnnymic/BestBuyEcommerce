package com.bestbuy.ecommerce.service;

public interface WishlistService {
   String userAddProductToFavourite(Long productId);

   String removeFromWishlist(Long productId);
}
