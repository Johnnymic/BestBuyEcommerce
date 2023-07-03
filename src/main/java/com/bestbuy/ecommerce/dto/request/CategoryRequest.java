package com.bestbuy.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryRequest {
    @NotNull(message = "Field cannot be missing or empty")
    private String name;
    private String imageUrl;


}
