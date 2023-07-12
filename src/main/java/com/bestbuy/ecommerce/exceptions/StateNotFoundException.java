package com.bestbuy.ecommerce.exceptions;

public class StateNotFoundException extends RuntimeException {

    public StateNotFoundException(String message) {
        super(message);
    }
}
