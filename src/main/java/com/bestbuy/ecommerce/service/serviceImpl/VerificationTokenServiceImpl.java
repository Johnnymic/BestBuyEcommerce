package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.VerificationToken;
import com.bestbuy.ecommerce.domain.repository.VerificationTokenRepository;

import com.bestbuy.ecommerce.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private  final VerificationTokenRepository verificationTokenRepository;
    @Override
    public void saveConfirmationToken(AppUser user, String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElse(null);

    }
}
