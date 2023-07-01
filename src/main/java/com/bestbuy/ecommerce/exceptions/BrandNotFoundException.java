package com.bestbuy.ecommerce.exceptions;

public class BrandNotFoundException extends  RuntimeException{

    public BrandNotFoundException(String message) {
        super("brand Not found Exception " + message);
    }
}
