package com.bestbuy.ecommerce.exceptions;

public class CategoryNotFoundException  extends  RuntimeException{

    public CategoryNotFoundException(String categoryNotFound) {

        super("User "+categoryNotFound);
    }
}

