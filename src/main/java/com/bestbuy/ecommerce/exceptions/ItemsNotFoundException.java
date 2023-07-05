package com.bestbuy.ecommerce.exceptions;

public class ItemsNotFoundException extends RuntimeException{

    public ItemsNotFoundException(String message) {
        super(message);
    }
}
