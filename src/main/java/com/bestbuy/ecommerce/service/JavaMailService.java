package com.bestbuy.ecommerce.service;

import org.springframework.http.ResponseEntity;

public interface JavaMailService {
    ResponseEntity<String> sendMail(String receiveMail , String subject, String text);


}
