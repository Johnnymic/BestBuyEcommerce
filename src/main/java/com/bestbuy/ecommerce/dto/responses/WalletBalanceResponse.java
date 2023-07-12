package com.bestbuy.ecommerce.dto.responses;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletBalanceResponse {
    private BigDecimal currentBalance;

}
