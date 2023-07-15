package com.bestbuy.ecommerce.domain.entity;

import com.bestbuy.ecommerce.enums.PickupStatus;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private Double grandTotal;

    private Double deliveryFee;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pickup_id")
    private PickupCenter pickupCenter;

    @Enumerated(EnumType.STRING)
    private PickupStatus pickupStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private  Transactions transactions;

    @OneToOne( cascade = CascadeType.ALL)
    private ModeOfPayment modeOfPayment;




}
