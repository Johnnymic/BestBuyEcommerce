package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.*;
import com.bestbuy.ecommerce.domain.repository.*;
import com.bestbuy.ecommerce.dto.request.OrderRequest;
import com.bestbuy.ecommerce.dto.responses.AdminOrderResponse;
import com.bestbuy.ecommerce.dto.responses.OrderResponse;
import com.bestbuy.ecommerce.enums.DeliveryStatus;
import com.bestbuy.ecommerce.enums.PaymentPurpose;
import com.bestbuy.ecommerce.enums.PickupStatus;
import com.bestbuy.ecommerce.enums.TransactionStatus;
import com.bestbuy.ecommerce.exceptions.*;
import com.bestbuy.ecommerce.service.CartService;
import com.bestbuy.ecommerce.service.OrderService;
import com.bestbuy.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        BigDecimal grandTotal= BigDecimal.valueOf(orderRequest.getGrandTotal().doubleValue());

        Address customerAddress = addressRepository.findByAndEmailAddress(orderRequest.getEmailAddress())
                .orElseThrow(()->new AddressNotFoundException("Address not found exception"));

        Order orders = new Order();
        orders.setAddress(customerAddress);
        orders.setPickupStatus(PickupStatus.YET_TO_BE_PICKED_UP);
        orders.setPickupCenter(center);
        orders.setGrandTotal(grandTotal.doubleValue());
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

    @Override
    public Page<AdminOrderResponse> viewOrderByPaginated(Integer pageNo, Integer pageSize, String sortBy) {
        List<Order> orders = orderRepository.findAll();
        List<AdminOrderResponse> orderResponseList = orders.stream()
                .map(order -> AdminOrderResponse.builder()
                        .firstName(order.getUser().getFirstName())
                        .lastName(order.getUser().getLastName())
                        .pickupCenterAddress(order.getPickupCenter().getAddress())
                        .phone(order.getUser().getPhone())
                        .pickupStatus(order.getPickupStatus())
                        .grandTotal(order.getGrandTotal())
                        .status(order.getPickupStatus().name())
                        .build()).collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC, sortBy);
        int min = pageNo*pageSize;
        int max = Math.max(pageSize*(pageNo+1),orderResponseList.size());
        return  new PageImpl<>(orderResponseList.subList(min,max),pageRequest,orderResponseList.size());
    }

    @Override
    public Page<OrderResponse> viewHistory(Integer pageNo, Integer pageSize) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()->new AppUserNotFountException("please login in"));

        Set<Order> orders = loginUser.getOrder();
        if(orders.isEmpty()){
            throw  new  OrderNotFoundException("Order not Found");
        }
         List<OrderResponse>  orderResponseList = orders.stream()
                 .map(order -> OrderResponse.builder()
                         .grandTotal(order.getGrandTotal())
                         .items(order.getOrderItems())
                         .deliveryStatus(DeliveryStatus.TO_ARRIVE)
                         .address(order.getAddress())
                         .pickupCenter(order.getPickupCenter())
                         .modeOfPayment(order.getModeOfPayment())
                         .deliveryFee(order.getDeliveryFee())
                         .build()).collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        int min = pageNo*pageSize;
        int max = Math.max(pageSize*(pageNo+1),orderResponseList.size());
        return new PageImpl<>(orderResponseList.subList(min,max),pageRequest,orderResponseList.size());
    }


}
