package com.bestbuy.ecommerce.dto.responses;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletInfoResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String walletBalance;
    private String baseCurrency;
}
