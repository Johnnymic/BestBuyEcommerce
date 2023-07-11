package com.bestbuy.ecommerce.dto.request;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletRequest {
    private BigDecimal amount;
    private String email;
}
