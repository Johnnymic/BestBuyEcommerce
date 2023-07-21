package com.bestbuy.ecommerce.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PayStackTransactions extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    private String reference ;

    private Boolean success;

    private String email;
}
