package com.bestbuy.ecommerce.exceptions;

public class OrderNotFoundException extends RuntimeException{


    public OrderNotFoundException(String message) {
        super(message);
    }
}
