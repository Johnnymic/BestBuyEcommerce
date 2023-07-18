package com.bestbuy.ecommerce.dto.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class AddPermissionRequest {
    private  String permission;
}
