package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.State;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private String fullName;
    private String phone;
    private String emailAddress;
    private String street;
    private State state;
    private String country;
}
