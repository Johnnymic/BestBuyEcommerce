package com.bestbuy.ecommerce.domain.entity;

import com.bestbuy.ecommerce.enums.BaseCurrency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Wallet {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long walletId;

 private BigDecimal accountBalance;

 private BaseCurrency baseCurrency;

 @OneToOne(mappedBy = "wallet", cascade = CascadeType.ALL)
 private AppUser appUser;



}
