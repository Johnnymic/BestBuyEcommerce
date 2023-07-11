package com.bestbuy.ecommerce.dto.responses;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletResponse {
    private String fullName;
    private BigDecimal depositAmount;
    private BigDecimal newBalance;

}
