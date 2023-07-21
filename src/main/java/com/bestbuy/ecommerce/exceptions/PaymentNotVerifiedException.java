package com.bestbuy.ecommerce.exceptions;

public class PaymentNotVerifiedException extends RuntimeException {

    public PaymentNotVerifiedException(String message) {
        super(message);
    }
}
