package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.LoginRequest;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.LoginResponse;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;

public interface UserService {
    RegistrationResponse registerUser(RegistrationRequest registrationResquest);

    LoginResponse authenticateUser(LoginRequest loginRequest);
}
