package com.bestbuy.ecommerce.context;

import org.springframework.security.core.context.SecurityContextHolder;

public class UsersContext {

    public static String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }
}
