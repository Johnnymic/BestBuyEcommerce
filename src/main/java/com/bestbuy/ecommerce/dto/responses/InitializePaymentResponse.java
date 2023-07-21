package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.Data;
import lombok.*;



@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitializePaymentResponse {
        private Boolean status;
        private String message;
        private Data data;
}
