package com.bestbuy.ecommerce.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private Long productId;

    private  int quality;

    private String message;
}
