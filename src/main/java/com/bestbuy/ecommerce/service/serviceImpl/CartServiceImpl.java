package com.bestbuy.ecommerce.service.serviceImpl;
import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Cart;
import com.bestbuy.ecommerce.domain.entity.CartItems;
import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.CartItemsRepository;
import com.bestbuy.ecommerce.domain.repository.CartRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.dto.request.CartRequest;

import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.ItemsNotFoundException;
import com.bestbuy.ecommerce.exceptions.NotAvailableException;
import com.bestbuy.ecommerce.exceptions.ProductNotFoundException;
import com.bestbuy.ecommerce.service.CartService;

import com.bestbuy.ecommerce.utitls.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
                addToItemsQualtity(cartRequest.getProductId());
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

    private void addToItemsQualtity(Long productId) {
        AppUser longInUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("User is not login to the application"));
        Cart cart =getOrCreateCart(longInUser);
        Set<CartItems> items = cart.getItems();

    }

    @Override
    public String deleteItemsFromCart(Long cartItemId) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(() -> new AppUserNotFountException("User not found"));

        Cart cart = getOrCreateCart(loginUser);

        Set<CartItems> cartItems = cart.getItems();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new ItemsNotFoundException("Cart is empty");
        }

        CartItems cartItem = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new ItemsNotFoundException("Item not found"));

        if (cartItems.contains(cartItem)) {
            cart.setTotalAmount(cart.getTotalAmount() - (cartItem.getOrderQty() * cartItem.getUnitPrice()));
            cartItemsRepository.delete(cartItem);
            cart.getItems().remove(cartItem);
            cartRepository.save(cart);
        }

        return "Cart item successfully deleted";
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
        for (CartItems cartItem : cart.getItems()) {
            cartTotal += cartItem.getSubTotal();
        }
        cart.setTotalAmount(cartTotal);
    }
    private CartItems mapToCartItems(Product product, Cart cart) {
        CartItems cartItem = new CartItems();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setImageUrl(product.getImageUrl());
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setQuantity(1);
        cartItem.setOrderQty(1);
        return cartItem;
    }


}