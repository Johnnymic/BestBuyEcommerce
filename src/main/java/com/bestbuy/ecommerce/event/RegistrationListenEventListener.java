package com.bestbuy.ecommerce.event;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

public class RegistrationListenEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        AppUser user = event.getAppUser();
        String token = UUID.randomUUID().toString();


    }
}
