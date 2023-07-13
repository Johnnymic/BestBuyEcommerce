package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.*;
import com.bestbuy.ecommerce.domain.repository.*;
import com.bestbuy.ecommerce.dto.request.OrderRequest;
import com.bestbuy.ecommerce.dto.responses.OrderResponse;
import com.bestbuy.ecommerce.enums.PaymentPurpose;
import com.bestbuy.ecommerce.enums.PickupStatus;
import com.bestbuy.ecommerce.enums.TransactionStatus;
import com.bestbuy.ecommerce.exceptions.*;
import com.bestbuy.ecommerce.service.CartService;
import com.bestbuy.ecommerce.service.OrderService;
import com.bestbuy.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.bestbuy.ecommerce.enums.PaymentPurpose.PURCHASE;
import static com.bestbuy.ecommerce.enums.TransactionStatus.COMPLETE;
import static com.bestbuy.ecommerce.enums.TransactionStatus.PENDING;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private  final AppUserRepository appUserRepository;

    private  final OrderItemsRepository orderItemsRepository;

    private final TransactionRepository transactionRepository;

    private  final  PickupCenterRepository pickupCenterRepository;

    private  final CartService cartService;

    private  final AddressRepository addressRepository;
    @Override
    public String saveOrder(OrderRequest orderRequest) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()->new AppUserNotFountException("user please longin "));
        Transactions transactions = Transactions.builder()
                .wallet(loginUser.getWallet())
                .Amount(orderRequest.getGrandTotal().toString())
                .paymentPurpose(PURCHASE)
                .reference(UUID.randomUUID().toString())
                .status(PENDING)
                .build();
        PickupCenter center = pickupCenterRepository.findByEmail(orderRequest.getPickupCenterEmail())
                .orElseThrow(()-> new CenterNotFoundException("center not found"));
        BigDecimal grandTotal= orderRequest.getGrandTotal();
        Address customerAddress = addressRepository.findByAndEmailAddress(orderRequest.getEmailAddress())
                .orElseThrow(()->new AddressNotFoundException("Address not found exception"));

        Order orders = new Order();
        orders.setAddress(customerAddress);
        orders.setPickupStatus(PickupStatus.YET_TO_BE_PICKED_UP);
        orders.setPickupCenter(center);
        orders.setTotalAmount(grandTotal);
        orders.setTransactions(transactions);

        if(orders== null){
            throw  new CustomerOrderNotFoundException("customer order not found");
        }

        Set<CartItems>cartItems = loginUser.getCart().getItems();
        if(cartItems.isEmpty()){
            throw  new CartNotFoundException("catItems not found ");
        }
        Set<OrderItem> orderItems = new HashSet<>();

        cartItems.forEach(items->{
            OrderItem orderItem = OrderItem.builder()
                    .order(orders)
                    .productName(items.getProductName())
                    .orderQty(items.getOrderQty())
                    .subTotal(items.getSubTotal())
                    .unitPrice(items.getUnitPrice())
                    .imageUrl(items.getImageUrl())
                    .build();
            orderItems.add(orderItem);
            orderItemsRepository.save(orderItem);
        });
        cartService.clearCart();
        orderRepository.save(orders);
        transactionRepository.save(transactions);
        return "order is save successfully";
    }
}
