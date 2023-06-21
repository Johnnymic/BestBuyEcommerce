package com.bestbuy.ecommerce.exceptions;

public class AppUserNotFountException extends RuntimeException{
    public AppUserNotFountException(String email) {

            super("User "+email+ " already registered");
        }

}
