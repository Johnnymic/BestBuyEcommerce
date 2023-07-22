package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.ResetPasswordRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface PasswordService {
    String  forgetPassword(String email, HttpServletRequest request);

   String  resetPassword(ResetPasswordRequest request);
}
