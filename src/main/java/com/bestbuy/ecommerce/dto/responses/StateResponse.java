package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.PickupCenter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StateResponse {
    private Long id;

    private String stateName;

    private Set<PickupCenter> pickupCenterSet;


}
