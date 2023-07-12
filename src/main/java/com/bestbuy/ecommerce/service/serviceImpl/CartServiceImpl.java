package com.bestbuy.ecommerce.service.serviceImpl;
import com.bestbuy.ecommerce.domain.entity.*;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.CartItemsRepository;
import com.bestbuy.ecommerce.domain.repository.CartRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.dto.request.CartRequest;

import com.bestbuy.ecommerce.dto.responses.CartResponse;
import com.bestbuy.ecommerce.exceptions.*;
import com.bestbuy.ecommerce.service.CartService;

import com.bestbuy.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;
    private final CartRepository cartRepository;

    @Override
    public String addItemsToCart(CartRequest cartRequest) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(() -> new AppUserNotFountException("User not found"));

        Cart cart = getOrCreateCart(loginUser);

        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (product.getQuantityAvailable() == 0 ) {
            throw new NotAvailableException("Product is out of stock");
            
        } else if (cartItemsRepository.findByProductId(cartRequest.getProductId())!=null) {
                addItemsToQuantity(cartRequest.getProductId());
        }

        CartItems newCartItem = mapToCartItems(product, cart);
        newCartItem.setSubTotal(newCartItem.getOrderQty() * product.getPrice());
        cartItemsRepository.save(newCartItem);
        cart.getItems().add(newCartItem);
        updateCartTotalAmount(cart);

        cartRepository.save(cart);
        appUserRepository.save(loginUser);

        return "Cart item successfully added";
    }



    @Override
    public String deleteItemsFromCart(Long cartItemId) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(() -> new AppUserNotFountException("User not found"));

        Cart cart = getOrCreateCart(loginUser);

        Set<CartItems> items = cart.getItems();
        if (cart == null) {
            throw new ItemsNotFoundException("Cart is empty");
        }

        CartItems cartItem = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new ItemsNotFoundException("Item not found"));


        if (items.contains(cartItem)) {
            cart.setTotalAmount(cart.getTotalAmount() - (cartItem.getOrderQty() * cartItem.getUnitPrice()));
            cartItemsRepository.delete(cartItem);
            cart.getItems().remove(cartItem);
            cartRepository.save(cart);
        }

        return "Cart item successfully deleted";
    }

    @Override
    public String addItemsToQuantity(Long productId) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(() -> new AppUserNotFountException("User not found"));


        Cart cart = getOrCreateCart(loginUser);
        Set<CartItems> cartItems = cart.getItems();
        Double cartTotal = cart.getTotalAmount();

        CartItems foundItems = cartItemsRepository.findByProductId(productId);

        if(foundItems.getProduct().getQuantityAvailable()==0) {
            throw new NotAvailableException("product not found ");
        }

               for (CartItems item : cartItems){
                      if (item.getProduct().getId() == foundItems.getProduct().getId()){
                          foundItems.setOrderQty(item.getOrderQty() +1 );
                          foundItems.setSubTotal(item.getUnitPrice() * item.getOrderQty());
                          cartItemsRepository.save(item);
                      }
               }
            cartTotal+= foundItems.getUnitPrice();
               cart.setTotalAmount(cartTotal);
              cartRepository.save(cart);

               return  "cart quantity has been be updated successfully";
    }

    @Override
    public String reduceItemQuantity(Long productId) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(() -> new AppUserNotFountException("User not found"));


        Cart cart = getOrCreateCart(loginUser);
        Set<CartItems> cartItems = cart.getItems();
        Double cartTotal = cart.getTotalAmount();
        CartItems foundItems = cartItemsRepository.findByProductId(productId);

        if(foundItems.getProduct().getQuantityAvailable()==0) {
            throw new NotAvailableException("product not found ");
        }
        for(CartItems item: cartItems){
            if(item.getProduct().getId() == foundItems.getProduct().getId()){
                foundItems.setOrderQty(item.getOrderQty()-1);
                foundItems.setSubTotal(item.getUnitPrice() * item.getOrderQty());
                cartItemsRepository.save(item);
            }
        }
        cartTotal-= foundItems.getUnitPrice();
        cartRepository.save(cart);
        cart.setTotalAmount(cartTotal);


        return "cart quantity has been reduce";
    }

    @Override
    public String clearCart() {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(() -> new AppUserNotFountException("User not found"));


        Cart cart = getOrCreateCart(loginUser);
        Set<CartItems> cartItems = cart.getItems();

            for(CartItems item : cartItems){
                cartItemsRepository.delete(item);
            }

            cartItems.clear();
            cart.setItems(cartItems);
            cart.setTotalAmount(0.0);
            cartRepository.save(cart);
        return "cart is clear successfully";
    }

    @Override
    public CartResponse viewCartByUser() {
        AppUser appUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(() -> new AppUserNotFountException("User not found"));
      Cart cart = cartRepository.findByUser(appUser);
      if(cart.getItems().isEmpty()){
          throw  new CartNotFoundException("cart not found Exception");
      }
      double cartTotal = cart.getTotalAmount();

        List<CartItems> cartItems = new ArrayList<>(cart.getItems());
        Collections.sort(cartItems, Comparator.comparing(CartItems::getId, Comparator.reverseOrder()));
         return CartResponse.builder()
                 .items(cartItems)
                 .total(cartTotal)
                 .message("cart is viewed")
                 .build();

    }
    private CartResponse mapToCartResponse(CartItems cart) {
        return CartResponse.builder()

                .message("list of cart")
                .build();

    }


    private Cart getOrCreateCart(AppUser user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setItems(new HashSet<>());
            cart.setUser(user);
            user.setCart(cart);
        }
        return cart;
    }

    private void updateCartTotalAmount(Cart cart) {
        Double cartTotal = 0.0;
        for (CartItems cartItem : cart.getItems())
            cartTotal += cartItem.getSubTotal();
        cart.setTotalAmount(cartTotal);
    }
    private CartItems mapToCartItems(Product product, Cart cart) {
        CartItems cartItem = new CartItems();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setImageUrl(product.getImageUrl());
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setOrderQty(1);
        return cartItem;
    }


}