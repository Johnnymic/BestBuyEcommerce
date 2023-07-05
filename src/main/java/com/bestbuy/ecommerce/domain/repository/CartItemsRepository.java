package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItems,Long> {


    CartItems findByProductId(Long productId);
}

