package com.bestbuy.ecommerce.dto.request;

import com.bestbuy.ecommerce.domain.entity.BaseEntity;
import com.bestbuy.ecommerce.domain.entity.Brand;
import com.bestbuy.ecommerce.domain.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

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

    private Category category;

    private Brand brand;

    private int quantityAvailable;
    private boolean isOutOfStock;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;
    private String imageUrl;
}
