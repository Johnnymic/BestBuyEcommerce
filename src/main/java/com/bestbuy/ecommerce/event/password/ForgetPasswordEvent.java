package com.bestbuy.ecommerce.event.password;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter

public class ForgetPasswordEvent extends ApplicationEvent {

    private  String eventUrl;

    private AppUser appUser;

    public ForgetPasswordEvent( String eventUrl, AppUser appUser) {
        super(appUser);
        this.eventUrl = eventUrl;
        this.appUser = appUser;
    }



}
