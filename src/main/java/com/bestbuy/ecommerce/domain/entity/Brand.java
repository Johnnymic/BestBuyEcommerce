package com.bestbuy.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "brands")


public class Brand  extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brandName;
    private String brandDescription;
    private String logoUrl;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;



}
