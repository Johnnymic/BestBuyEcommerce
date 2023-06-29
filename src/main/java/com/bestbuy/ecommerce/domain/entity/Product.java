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
public class Product {

       @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
       private String name;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "category_id", nullable = false)
        private Category category ;


        private BigDecimal price;

        private String description;

        private String imageUrl;


}
