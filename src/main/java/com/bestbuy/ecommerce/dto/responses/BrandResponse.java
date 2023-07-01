package com.bestbuy.ecommerce.dto.responses;

import com.bestbuy.ecommerce.domain.entity.Product;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {
    private String brandName;

    private String brandDescription;
    @CreatedBy
    private LocalDateTime establishedDate;
}
