package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.State;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PickUpCenterResponse {
    private String name;
    private String location;
    private State state;
    private String email;
    private String phone;

    private String address;

    private  Double delivery;
}
