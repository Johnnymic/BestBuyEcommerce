package com.bestbuy.ecommerce.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Range;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @DecimalMin(value="0.0", message="Field Price cannot be blank")
    private Double price;

    @NotBlank(message = "Field ImageUrl cannot be blank")
    private String imageUrl;

    @Range(min=0, message = "Field Available Quantity cannot be blank")
    private Integer availableQty;

    @NotBlank(message="Field SubCategory cannot be null")
    private String subCategory;

    @NotBlank(message="Field SubCategory cannot be null")
    private String brand;


    @NotBlank(message = "Field Description cannot be blank")
    private String description;
}
