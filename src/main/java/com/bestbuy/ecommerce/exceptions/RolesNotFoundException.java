package com.bestbuy.ecommerce.exceptions;

import javax.management.relation.RoleNotFoundException;

public class RolesNotFoundException extends RuntimeException {

    public RolesNotFoundException(String message) {
        super(message);
    }
}
