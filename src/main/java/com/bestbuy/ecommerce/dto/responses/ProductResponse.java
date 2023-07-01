package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.BaseEntity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse  {

    private String productName;

    private String description;

    private String categoryName;

    private String brandName;

    private BigDecimal price;

    private int quantityAvailable;
    private boolean isOutOfStock;

    private LocalDateTime updateAt;

    private LocalDateTime createdAt;


}
