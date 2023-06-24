package com.bestbuy.ecommerce.event;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private String applicationUrl;

    private AppUser AppUser;

    public RegistrationCompleteEvent(String applicationUrl, AppUser appUser) {
        super(appUser);
        this.applicationUrl = applicationUrl;
        AppUser = appUser;
    }
}
