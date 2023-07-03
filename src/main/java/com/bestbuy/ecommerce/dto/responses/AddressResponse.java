package com.bestbuy.ecommerce.dto.responses;

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
    private String state;
    private String country;
}
