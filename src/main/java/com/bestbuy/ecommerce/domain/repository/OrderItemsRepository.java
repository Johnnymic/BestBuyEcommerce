package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Long> {
}
