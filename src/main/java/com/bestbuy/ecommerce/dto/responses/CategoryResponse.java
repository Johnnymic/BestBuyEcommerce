package com.bestbuy.ecommerce.dto.responses;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CategoryResponse  {
    private String categoryName;
    private Date createAt;

    private Date updateAt;

}
