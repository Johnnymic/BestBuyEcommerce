package com.bestbuy.ecommerce.utitls;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtils {
    public static String getUserEmailFromContext(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}