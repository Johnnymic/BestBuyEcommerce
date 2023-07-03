package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Cart;
import com.bestbuy.ecommerce.domain.entity.CartItems;
import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.CartItemsRepository;
import com.bestbuy.ecommerce.domain.repository.CartRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.dto.CartItemsResponse;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.NotAvailableException;
import com.bestbuy.ecommerce.exceptions.ProductNotFoundException;
import com.bestbuy.ecommerce.service.CartService;
import com.bestbuy.ecommerce.utitls.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


    private  final AppUserRepository appUserRepository;

    private final ProductRepository productRepository;

    private final CartItemsRepository cartItemsRespository;

    private final CartRepository cartRepository;



    @Override
    public String  addItemsToCart(Long productId) {
       AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
               .orElseThrow(()-> new AppUserNotFountException("User not found"));
       Cart cart = loginUser.getCart();
       Product product = productRepository.findById(productId)
               .orElseThrow(()-> new ProductNotFoundException("product not found"));
       Set<CartItems> allCartItems = cart.getItems();


      if(product.getQuantityAvailable() == 0){
       throw  new NotAvailableException("product is out of stock ");
      }
      CartItems newItems =mapToCartItems(product,cart);
      newItems.setSubTotal(newItems.getOrderQty()* product.getPrice());
      cartItemsRespository.save(newItems);
        allCartItems.add(newItems);
        cart.setItems(allCartItems);

        Double  cartTotal= 0.0;
        for(CartItems cartItems : cart.getItems()){
            cartTotal+=cartItems.getSubTotal();
        }
         cart.setTotalAmount(cartTotal);
         cartRepository.save(cart);
         appUserRepository.save(loginUser);



        return  "cart is successfully added";
    }

    private CartItems mapToCartItems(Product product,Cart cart) {
    CartItems newCart = CartItems.builder()
                .product(product)
                .cart(cart)
                .imageUrl(product.getImageUrl())
                .unitPrice(product.getPrice())
                .quantity(1)
                .build();


     return newCart;

    }
}
