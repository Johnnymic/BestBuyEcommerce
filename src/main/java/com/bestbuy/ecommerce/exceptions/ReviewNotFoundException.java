package com.bestbuy.ecommerce.exceptions;

public class ReviewNotFoundException extends RuntimeException {


    public ReviewNotFoundException(String message) {
        super(message);
    }
}
