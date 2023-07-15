package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.Address;
import com.bestbuy.ecommerce.domain.entity.PickupCenter;
import com.bestbuy.ecommerce.enums.PickupStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AdminOrderResponse {
    private String firstName;
    private String lastName;
    private String phone;
    private Double grandTotal;
    private String status;
    private PickupStatus pickupStatus;
    private String pickupCenterName;
    private String pickupCenterAddress;
}
