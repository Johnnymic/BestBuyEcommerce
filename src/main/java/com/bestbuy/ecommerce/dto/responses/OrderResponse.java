package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.Address;
import com.bestbuy.ecommerce.domain.entity.ModeOfPayment;
import com.bestbuy.ecommerce.domain.entity.OrderItem;
import com.bestbuy.ecommerce.domain.entity.PickupCenter;
import com.bestbuy.ecommerce.enums.DeliveryStatus;
import com.bestbuy.ecommerce.enums.ModeOfDelivery;
import org.hibernate.Transaction;

import java.util.Set;

public class OrderResponse {
    private ModeOfPayment modeOfPayment;
    private Set<OrderItem> items;
    private Double deliveryFee;
    private ModeOfDelivery modeOfDelivery;
    private DeliveryStatus deliveryStatus;
    private Double grandTotal;
    private Double discount;
    private Address address;
    private Transaction transaction;
    private PickupCenter pickupCenter;
}
