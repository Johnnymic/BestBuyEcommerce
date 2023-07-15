package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.Address;
import com.bestbuy.ecommerce.domain.entity.ModeOfPayment;
import com.bestbuy.ecommerce.domain.entity.OrderItem;
import com.bestbuy.ecommerce.domain.entity.PickupCenter;
import com.bestbuy.ecommerce.enums.DeliveryStatus;
import com.bestbuy.ecommerce.enums.ModeOfDelivery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.Transaction;

import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderResponse {
    private ModeOfPayment modeOfPayment;
    private Set<OrderItem> items;
    private Double deliveryFee;

    private DeliveryStatus deliveryStatus;
    private Double grandTotal;
    private Double discount;
    private Address address;
    private Transaction transaction;
    private PickupCenter pickupCenter;
}
