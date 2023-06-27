package com.bestbuy.ecommerce.utitls;

import jakarta.servlet.http.HttpServletRequest;

public class EmailUtils {

    public static  String applicationUrl(HttpServletRequest request){
        return request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
