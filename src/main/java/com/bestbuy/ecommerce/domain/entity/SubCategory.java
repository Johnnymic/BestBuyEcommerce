package com.bestbuy.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "categories")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;


    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;


}
