package com.bestbuy.ecommerce.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandSearchRequest {
    private  String brandName;

    private  String brandDescription;
}
