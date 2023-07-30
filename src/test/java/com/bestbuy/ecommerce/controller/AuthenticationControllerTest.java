package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.UserData;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {





    @Test
    void authenticate() {
    }

    @Test
   public void toRegisterUser() {
        RegistrationRequest request = UserData.buildRegisterUser();
        RegistrationResponse response = UserData.buildUserRegisterResponse();



    }
}