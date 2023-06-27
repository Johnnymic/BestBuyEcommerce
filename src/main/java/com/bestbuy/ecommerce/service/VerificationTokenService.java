package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.domain.entity.VerificationToken;
import jakarta.servlet.http.HttpServletRequest;

public interface VerificationTokenService {

    void saveConfirmationToken(VerificationToken verificationToken);


    String verifyUser(String token, HttpServletRequest request);

    String sendUserVerficationMail(String email, HttpServletRequest request);
}
