package com.bestbuy.ecommerce.event;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.VerificationToken;
import com.bestbuy.ecommerce.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Component
@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationListenEventListener implements ApplicationListener<RegistrationCompleteEvent> {


    private final VerificationTokenService verificationTokenService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        AppUser user = event.getAppUser();
        String token = UUID.randomUUID().toString();
        VerificationToken verification_token = new VerificationToken(token,user);
        verificationTokenService.saveConfirmationToken(verification_token);
        String url = event.getApplicationUrl()+ "/token"+ token;



        log.info("click the link to verify your registration : {} " +url);
    }



}
