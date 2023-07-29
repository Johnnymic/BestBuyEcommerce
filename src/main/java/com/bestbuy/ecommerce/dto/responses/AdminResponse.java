package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.enums.Roles;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminResponse {
    private String firstName;
    private String nickName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    private Roles role;
    private String position;
}
