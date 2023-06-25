package com.bestbuy.ecommerce.event;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Component
@Service
@RequiredArgsConstructor
public class RegistrationListenEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenService verificationTokenService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        AppUser user = event.getAppUser();
        String token = UUID.randomUUID().toString();

        verificationTokenService.saveConfirmationToken(user,token);

    }
}
