package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.domain.entity.AppUser;

public interface VerificationTokenService {
    void saveConfirmationToken(AppUser user, String token);
}
