package com.bestbuy.ecommerce.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResetPasswordRequest {
    private String email;
    private String password;
}
