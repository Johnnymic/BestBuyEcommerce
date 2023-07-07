package com.bestbuy.ecommerce.dto.request;

import com.bestbuy.ecommerce.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileRequest {
    @NotNull(message = "Please Provide firstname")
    private String firstName;

    @NotNull(message = "Please Provide lastname")
    private String lastName;

    @Email
    @NotNull(message = "Please select your gender")
    private String email;

    private String date_of_birth;

    private String phone;

    private Gender gender;
}
