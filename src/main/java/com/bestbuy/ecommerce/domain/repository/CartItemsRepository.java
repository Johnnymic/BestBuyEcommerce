package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
}

