package com.bestbuy.ecommerce.dto.request;

import com.bestbuy.ecommerce.domain.entity.Product;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubCategoryRequest {
    private Long subCategoryId;
    @NotNull(message = "Field cannot be missing or empty")
    private String name;

    private Long CategoryId;
    private String imageUrl;

    private Product productId;


}
