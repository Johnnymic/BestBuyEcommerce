package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.Order;
import com.bestbuy.ecommerce.enums.DeliveryStatus;
import com.bestbuy.ecommerce.enums.PickupStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order,Long> {

    Page<Order> findByDeliveryStatus(DeliveryStatus status, Pageable pageable);

    Page<Order>findByUserIdAndPickupStatus(Long userId, PickupStatus  status, Pageable pageable);
}
