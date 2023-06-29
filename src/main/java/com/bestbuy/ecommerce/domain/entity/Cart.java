package com.bestbuy.ecommerce.domain.entity;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.CartItems;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;



    @Column(nullable = false)
    private BigDecimal totalAmount;

    // Constructors, getters, and setters

    // ...
}