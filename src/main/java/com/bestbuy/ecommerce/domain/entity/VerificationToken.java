package com.bestbuy.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerificationToken {

    private static  final int EXPIRATION =10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long token_id;
    private String token;

    private Date  expiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appUser_token")
    private  AppUser appUser;

    public VerificationToken(String token) {
        this.token = token;
    }

    public VerificationToken(String token, Date expiration, AppUser appUser) {
        this.token = token;
        this.expiration = getExpirationDate();
        this.appUser = appUser;
    }

    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE , EXPIRATION);
        return  new Date(calendar.getTime().getTime());
    }

}
