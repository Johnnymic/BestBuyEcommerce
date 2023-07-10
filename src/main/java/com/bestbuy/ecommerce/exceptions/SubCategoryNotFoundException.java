package com.bestbuy.ecommerce.exceptions;

public class SubCategoryNotFoundException extends RuntimeException {

    public SubCategoryNotFoundException(String message) {
        super(message);
    }
}
