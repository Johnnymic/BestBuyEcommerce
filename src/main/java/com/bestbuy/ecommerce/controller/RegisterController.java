package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import com.bestbuy.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class RegisterController {

    private  final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse>registerUser(@RequestBody RegistrationRequest registrationResquest) {
        RegistrationResponse registerUser = userService.registerUser(registrationResquest);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }



}
