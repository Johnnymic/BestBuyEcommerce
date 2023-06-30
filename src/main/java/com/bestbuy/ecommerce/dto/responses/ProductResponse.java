package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.BaseEntity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse  {

    private String name;

    private String description;

    private String categoryName;

    private LocalDateTime updateAt;

    private LocalDateTime createdAt;


}
