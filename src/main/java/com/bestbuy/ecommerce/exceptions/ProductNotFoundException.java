package com.bestbuy.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RuntimeException {
    private String message;

    private HttpStatus status;

    public ProductNotFoundException(String message, String message1, HttpStatus status) {
        super(message);
        this.message = message1;
        this.status = status;
    }

    public ProductNotFoundException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ProductNotFoundException(String message) {
        this.message = message;
    }

}


