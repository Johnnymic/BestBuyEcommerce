package com.bestbuy.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Admin  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Admin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appUser_id")
    private  AppUser appUser;


}
