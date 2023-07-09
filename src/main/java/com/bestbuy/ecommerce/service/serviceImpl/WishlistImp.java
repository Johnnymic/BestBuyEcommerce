package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.entity.Wishlist;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.domain.repository.WishlistRepository;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.ProductAlreadyExistException;
import com.bestbuy.ecommerce.exceptions.ProductNotFoundException;
import com.bestbuy.ecommerce.service.WishlistService;
import com.bestbuy.ecommerce.utitls.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WishlistImp implements WishlistService {
    private final AppUserRepository appUserRepository;

    private  final ProductRepository productRepository;

    private final WishlistRepository wishlistRepository;
    @Override
    public String userAddProductToFavourite(Long productId) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("User not login "));
        Wishlist wishlist = loginUser.getWishlist();
        if(wishlist== null){
             wishlist= new Wishlist();
             wishlist.setUser(loginUser);
            wishlist.setProducts(new HashSet<>());
        }

        Set< Product> customerProduct = wishlist.getProducts();
        Product exitingProduct = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
        if(customerProduct.contains(exitingProduct)){
            throw  new ProductAlreadyExistException("product already exitsing ");
        }
        customerProduct.add(exitingProduct);
        loginUser.setWishlist(wishlist);
        appUserRepository.save(loginUser);
        wishlistRepository.save(wishlist);


        return "customer has added his favourite products";
    }

    @Override
    public String removeFromWishlist(Long productId) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("User not login "));
        Wishlist wishlist = loginUser.getWishlist();
        if(wishlist== null){
            wishlist= new Wishlist();
            wishlist.setUser(loginUser);
            wishlist.setProducts(new HashSet<>());
        }
        Set< Product> customerProduct = wishlist.getProducts();
        Product exitingProduct = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

        customerProduct.remove(exitingProduct);
        loginUser.setWishlist(wishlist);
        appUserRepository.save(loginUser);
        wishlistRepository.save(wishlist);

        return "product remove from wishlist";
    }

}

