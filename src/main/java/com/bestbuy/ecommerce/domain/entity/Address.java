package com.bestbuy.ecommerce.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;



@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String phone;
    private String emailAddress;
    private String street;
    private String state;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @JsonIgnore
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Order order;









}
