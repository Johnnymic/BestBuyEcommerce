package com.bestbuy.ecommerce.dto.request;

import com.bestbuy.ecommerce.domain.entity.Product;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
    private String brandName;

    private String brandDescription;

    private String logoUrl;

}
