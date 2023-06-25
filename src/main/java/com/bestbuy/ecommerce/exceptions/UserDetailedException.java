package com.bestbuy.ecommerce.exceptions;

public class UserDetailedException extends RuntimeException {
    public UserDetailedException(String userAccount) {
       super("user"+ userAccount + "is disabled");
    }
}

