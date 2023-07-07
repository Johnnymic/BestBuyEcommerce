package com.bestbuy.ecommerce.dto.responses;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileResponse {

    private String firstName;


    private String lastName;


    private String email;

    private String date_of_birth;

    private String phone;

    private String address;
}
