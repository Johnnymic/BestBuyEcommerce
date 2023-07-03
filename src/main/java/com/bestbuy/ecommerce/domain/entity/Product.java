package com.bestbuy.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product  extends  BaseEntity {

       @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
       private String productName;
       private Double price;
       private String description;
       private String imageUrl;

      private int quantityAvailable;
      private boolean isOutOfStock;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategory category ;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

}
