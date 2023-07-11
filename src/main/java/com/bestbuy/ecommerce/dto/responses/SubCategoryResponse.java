package com.bestbuy.ecommerce.dto.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryResponse {
    private Long subCategoryId;

    private String subCategoryName;

    private String imageUrl;


}
