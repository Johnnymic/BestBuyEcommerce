package com.bestbuy.ecommerce;

import com.bestbuy.ecommerce.dto.request.LoginRequest;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.LoginResponse;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import com.bestbuy.ecommerce.enums.Gender;
import com.bestbuy.ecommerce.enums.Roles;

public class UserData {
    private UserData() {};

    public static RegistrationRequest buildRegisterUser() {
        return RegistrationRequest.builder()
                .firstName("michael")
                .lastName("john")
                .password("12345678")
                .phone("08165876597")
                .email("johnny@@gmail.com")
                .gender(Gender.valueOf("MALE"))
                .dateOfBirth("12/5/2020")
                .role(Roles.USER)
                .build();

    }
    public static RegistrationResponse buildUserRegisterResponse() {
        return RegistrationResponse.builder()
                .firstName("michael")
                .lastName("john")
                .message("Registration successful")
                .build();

    }

    public static LoginRequest buildLoginRequest() {
        return LoginRequest.builder()
                .email("johnny@@gmail.com")
                .password("password123")
                .build();
    }

    public static LoginResponse buildLoginResponse() {
        return LoginResponse.builder()
                .refreshToken("efhkuegfwufeepuaHWWAETHGV")
                .refreshToken("yryrygwgdshflylfqrl")
                .build();

    }
}
