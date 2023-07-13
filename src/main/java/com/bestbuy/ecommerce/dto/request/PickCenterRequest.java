package com.bestbuy.ecommerce.dto.request;

import com.bestbuy.ecommerce.domain.entity.State;
import lombok.*;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class PickCenterRequest {
    private String name;
    private String location;
    private Long stateId;
    private String email;
    private String phone;

    private String address;

    private  Double delivery;
}
