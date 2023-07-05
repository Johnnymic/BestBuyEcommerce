package com.bestbuy.ecommerce.dto.request;

import com.bestbuy.ecommerce.domain.entity.BaseEntity;
import com.bestbuy.ecommerce.enums.Gender;
import com.bestbuy.ecommerce.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest  {
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Pattern(regexp = "\\d{14}", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Password is required")
    private String password;

    private String  dateOfBirth;

    private Gender gender;

    private Roles role;
}
