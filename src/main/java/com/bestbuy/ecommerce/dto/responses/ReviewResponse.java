package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Product product;
    private String title;
    private String comment;
    private int rating;
    private AppUser user;

    private Date updateAt;

    private Date createdAt;
}
