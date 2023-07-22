package com.bestbuy.ecommerce.utils;

import jakarta.servlet.http.HttpServletRequest;

public class EmailUtils {

    public static  String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

    public static String  forgetPasswordUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":5173"+request.getServerPort()+request.getContextPath();
    }
}
