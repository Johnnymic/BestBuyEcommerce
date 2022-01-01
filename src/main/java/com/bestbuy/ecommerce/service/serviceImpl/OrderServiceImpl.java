package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.*;
import com.bestbuy.ecommerce.domain.repository.*;
import com.bestbuy.ecommerce.dto.request.OrderRequest;
import com.bestbuy.ecommerce.dto.responses.AdminOrderResponse;
import com.bestbuy.ecommerce.dto.responses.OrderResponse;
import com.bestbuy.ecommerce.enums.DeliveryStatus;
import com.bestbuy.ecommerce.enums.PickupStatus;
import com.bestbuy.ecommerce.exceptions.*;
import com.bestbuy.ecommerce.service.CartService;
import com.bestbuy.ecommerce.service.OrderService;
import com.bestbuy.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bestbuy.ecommerce.enums.PaymentPurpose.PURCHASE;
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

    // ... (imports and other methods)

    @Override
    public boolean hasUserOrderedProduct(Long userId, Long productId) {
        // Get the user based on the provided userId
        AppUser loginUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new AppUserNotFountException("User not found"));

        // Check if the user has any orders containing the specified product
        return loginUser.getOrder().stream()
                .flatMap(order -> order.getOrderItems().stream())
                .anyMatch(orderItem -> orderItem.getProduct().getId().equals(productId));
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

    @Override
    public OrderResponse viewParticularOrder(Long orderId) {
        Order orders = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("order does not found "));

        return mapToOrderResponse(orders);
    }

    @Override
    public Page<OrderResponse> viewOrderByDeliveryStatus(DeliveryStatus status, Integer pageNo, Integer pageSize) {
       Integer pageNumbers= Math.max(pageNo,0);
       Integer pageSizes =Math.max(pageSize,1);
       PageRequest pageRequest = PageRequest.of(pageNumbers,pageSizes);
        return orderRepository.findByDeliveryStatus(status,pageRequest).map(this::mapToOrderResponse);

    }

    @Override
    public Page<OrderResponse> viewOrderPickupStatus(PickupStatus pickupStatus, Integer pageNo, Integer pageSize) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("user is not login in "));
        pageNo = Math.max(pageSize,1);
        pageSize= Math.max(pageNo,0);
        PageRequest pageable = PageRequest.of(pageNo,pageSize);

        return orderRepository.findByUserIdAndPickupStatus(loginUser.getId(),pickupStatus,pageable)
                .map(this::mapToOrderResponse);
    }


    private OrderResponse mapToOrderResponse(Order orders) {
        return  OrderResponse.builder()
                .orderId(orders.getId())
                .address(orders.getAddress())
                .pickupCenter(orders.getPickupCenter())
                .items(orders.getOrderItems())
                .deliveryStatus(DeliveryStatus.TO_ARRIVE)
                .build();

    }


}
