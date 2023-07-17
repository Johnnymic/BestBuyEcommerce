package com.bestbuy.ecommerce.dto.request;

import com.bestbuy.ecommerce.domain.entity.BaseEntity;
import com.bestbuy.ecommerce.domain.entity.Brand;
import com.bestbuy.ecommerce.domain.entity.SubCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
public class ProductRequest extends BaseEntity{
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;


//    @NotNull(message = "Category is required")
//    private Long categoryId;

//    @NotNull(message = "Category is required")
//    private Long brandId;

    private SubCategory category;

    private Brand brand;

    private int quantityAvailable;
    private boolean isOutOfStock;

    private Integer sales;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;
    private String imageUrl;
}
