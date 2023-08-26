package com.bestbuy.ecommerce.search;

import com.bestbuy.ecommerce.domain.entity.Brand;
import com.bestbuy.ecommerce.dto.request.BrandSearchRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BrandSearchDao {

    private final EntityManager entityManager;

   public  List<Brand> findAllBrandDao(
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

    public List<Brand>brandSearchCriteria(
       BrandSearchRequest searchRequest

    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);

        Root<Brand> root = criteriaQuery.from(Brand.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!searchRequest.getBrandName().isEmpty() || searchRequest.getBrandName()!= null) {
            Predicate predicate = criteriaBuilder.like(root.get("brandName"), "%" + searchRequest.getBrandName() + "%");
            predicates.add(predicate);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Brand> brandTypedQuery = entityManager.createQuery(criteriaQuery);
       return brandTypedQuery.getResultList();


    }


}
