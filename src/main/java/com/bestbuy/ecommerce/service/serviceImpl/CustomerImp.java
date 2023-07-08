package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.ProductAlreadyExistException;
import com.bestbuy.ecommerce.exceptions.ProductNotFoundException;
import com.bestbuy.ecommerce.service.CustomerService;
import com.bestbuy.ecommerce.utitls.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerImp implements CustomerService {
    private final AppUserRepository appUserRepository;

    private  final ProductRepository productRepository;
    @Override
    public String userAddProductToFavourite(Long productId) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("User not login "));
        Product exitingProduct = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
       Set< Product> customerProduct = loginUser.getFavorites();

       if(customerProduct.contains(exitingProduct)){
           throw new ProductAlreadyExistException("product is already exist");
       }
       customerProduct.add(exitingProduct);
       loginUser.setFavorites(customerProduct);
       appUserRepository.save(loginUser);

        return "customer has added his favourite products";
    }
}

