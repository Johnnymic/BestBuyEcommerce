package com.bestbuy.ecommerce.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Customer  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "person_id")
//    private AppUser person;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="cart_id")
//    private Cart cart = new Cart();
//
//
//
////    @JsonIgnore
////    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
////    private Set<Item> items;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<Order> orders;
//
//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL)
//    private Set<Product> favorites = new HashSet<>();
//
////    @JsonIgnore
////    @OneToMany(mappedBy = "customer",  fetch = FetchType.EAGER)
////    private Set<Address> addressBook;
//    private boolean isActive = true;


}
