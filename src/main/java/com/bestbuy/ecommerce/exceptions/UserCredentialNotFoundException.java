package com.bestbuy.ecommerce.exceptions;

public class UserCredentialNotFoundException extends RuntimeException {
    public UserCredentialNotFoundException(String passwordDoesNotMatch) {
       super("user not  found "+ passwordDoesNotMatch);
    }
}
