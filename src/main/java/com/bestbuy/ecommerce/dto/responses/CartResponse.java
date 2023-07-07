package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.CartItems;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartResponse {
  String message = "cart is successfully added";
  private List<CartItems> items;
  private Double total;
}
