package com.bestbuy.ecommerce.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;

import java.util.List;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart extends  BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @JsonIgnore
    @OneToMany(mappedBy = "cart" , orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CartItems> items = new HashSet<>();

    @Column(nullable = false)
    private Double totalAmount = 0.0;


}
