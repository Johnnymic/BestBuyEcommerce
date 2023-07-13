package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.PickupCenter;
import lombok.*;

import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StateResponse {
    private Long id;

    private String stateName;

    private Set<PickupCenter> pickupCenterSet;


}
