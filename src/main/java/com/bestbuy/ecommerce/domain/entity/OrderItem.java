package com.bestbuy.ecommerce.domain.entity;

import com.bestbuy.ecommerce.domain.entity.Order;
import com.bestbuy.ecommerce.domain.entity.Product;
import jakarta.persistence.*;


import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem extends  BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;


}
