package com.bestbuy.ecommerce.exceptions;

public class CustomerWalletNotFoundException extends RuntimeException {

    public CustomerWalletNotFoundException(String message) {
        super(message);
    }
}
