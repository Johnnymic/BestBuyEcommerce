package com.bestbuy.ecommerce.search;

import com.bestbuy.ecommerce.domain.entity.Brand;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BrandSearchDao {

    private final EntityManager entityManager;

    List<Brand> findAllBrandDao(
            String brandName,
            String brandDescription
    ){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);

        //SELECT * FROM  brand
        Root<Brand> root = criteriaQuery.from(Brand.class);

        //where clause for instance where brandName like "%brandName%"

        Predicate brandNamePredicate = criteriaBuilder.like(root.get("brandName"), "%" +brandName + "%");
        Predicate brandDescriptionPredicate = criteriaBuilder.like(root.get("brandDescription") ,"%" +brandDescription + "%");

        criteriaQuery.where(criteriaBuilder.or(brandDescriptionPredicate,brandNamePredicate));
        // get the resulted query

        TypedQuery<Brand> typedQuery = entityManager.createQuery(criteriaQuery);
       return typedQuery.getResultList();


    }

}
