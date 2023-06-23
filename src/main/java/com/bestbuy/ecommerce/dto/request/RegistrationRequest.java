package com.bestbuy.ecommerce.dto.request;

import lombok.*;

import javax.management.relation.Role;

@Getter
@Setter
@Builder

public class RegistrationRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    private String password;

    private Role role;
}
