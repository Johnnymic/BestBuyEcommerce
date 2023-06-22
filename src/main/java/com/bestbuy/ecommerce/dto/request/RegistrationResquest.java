package com.bestbuy.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResquest {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    private String password;

    private Role role;
}
