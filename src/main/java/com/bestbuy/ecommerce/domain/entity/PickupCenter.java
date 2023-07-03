package com.bestbuy.ecommerce.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pickupCenter_tbl")
public class PickupCenter extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickupId;


    private String name;
    private String address;
    private String email;
    private String phone;
    private Double delivery;

    @ManyToOne
    @JoinColumn(name = "states_id")
    private State state;

    @JsonIgnore
    @OneToOne(mappedBy = "pickupCenter", cascade = CascadeType.ALL)
    private Order order;
}
