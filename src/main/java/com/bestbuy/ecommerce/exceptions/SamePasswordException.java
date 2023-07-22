package com.bestbuy.ecommerce.exceptions;

public class SamePasswordException extends RuntimeException {

    public SamePasswordException(String message) {
        super(message);
    }
}
