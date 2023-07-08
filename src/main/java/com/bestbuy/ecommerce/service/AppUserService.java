package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.EditProfileRequest;
import com.bestbuy.ecommerce.dto.request.LoginRequest;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.EditProfileResponse;
import com.bestbuy.ecommerce.dto.responses.LoginResponse;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import com.bestbuy.ecommerce.dto.responses.UserProfileResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface AppUserService {
    RegistrationResponse registerUser(RegistrationRequest registrationResquest, HttpServletRequest request);

    LoginResponse authenticateUser(LoginRequest loginRequest);

    EditProfileResponse editProfile(EditProfileRequest editProfileRequest);

    UserProfileResponse viewUserProfile();

    Page<UserProfileResponse> viewAllUserProfilesByPaginationAndSort(Integer pageNo, Integer pageSize, String sortBy);
}
