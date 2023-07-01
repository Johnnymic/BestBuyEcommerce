package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

public class CategoryResponse {
    private String categoryName;


}
