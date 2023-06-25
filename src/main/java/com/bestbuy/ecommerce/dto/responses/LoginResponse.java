package com.bestbuy.ecommerce.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String userFullName;
    private String accessToken;
    private String refreshToken;
    private String message;
}
