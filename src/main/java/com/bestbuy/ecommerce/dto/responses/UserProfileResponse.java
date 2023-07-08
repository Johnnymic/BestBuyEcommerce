package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.enums.Gender;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String firstName;


    private String lastName;


    private String email;

    private String date_of_birth;

    private String phone;

    private Gender gender;

    private String address;
}
