package com.bestbuy.ecommerce.dto.responses;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryResponse {
    private Long subCategoryId;

    private String subCategoryName;

    private String imageUrl;

    private Date createAt;

    private Date updateAt;




}
