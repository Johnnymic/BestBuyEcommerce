package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.BaseEntity;

import com.bestbuy.ecommerce.domain.entity.Brand;
import com.bestbuy.ecommerce.domain.entity.SubCategory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse  {

    private String productName;

    private String description;

    private SubCategory subCategoryName;

    private Brand brandName;

    private Double price;

    private int quantityAvailable;
    private boolean isOutOfStock;

    private Date updateAt;

    private Date createdAt;


}
