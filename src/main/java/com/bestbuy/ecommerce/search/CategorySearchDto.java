package com.bestbuy.ecommerce.search;

import com.bestbuy.ecommerce.domain.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@RequiredArgsConstructor
@Repository
@Getter
public class CategorySearchDto {

    private final EntityManager  entityManager;

    private CriteriaBuilder criteriaBuilder;

   public    Page<Category> searchCategoryCriteria(CategorySearchPage categorySearchPage , CategorySearch categorySearch){
        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);

        Predicate predicate = getCategoryPredicate(categorySearch, categoryRoot);
        criteriaQuery.where(predicate);
        setSearchOrder(categorySearchPage,categoryRoot,criteriaQuery);
        TypedQuery<Category> categoryTypedQuery = entityManager.createQuery(criteriaQuery);
        categoryTypedQuery.setFirstResult(categorySearchPage.getPageNo()* categorySearchPage.getPageSize());
        categoryTypedQuery.setMaxResults(categorySearchPage.getPageSize());

        Pageable pageable =getPageRequest(categorySearchPage);
        Long categoryCount = getNumberOfCategory(predicate);


        return new PageImpl<>(categoryTypedQuery.getResultList(),pageable,categoryCount);

    }

    private Long getNumberOfCategory(Predicate predicate) {
        CriteriaQuery<Long> longCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Category> categoryRoot = longCriteriaQuery.from(Category.class);
        longCriteriaQuery.select(criteriaBuilder.count(categoryRoot)).where(predicate);
        return entityManager.createQuery(longCriteriaQuery).getSingleResult();
    }

    private Pageable getPageRequest(CategorySearchPage categorySearchPage) {
        Sort sorBy = Sort.by(categorySearchPage.getSortDirection(), categorySearchPage.getSortByName());
        return PageRequest.of(categorySearchPage.getPageNo(), categorySearchPage.getPageSize(), sorBy);
    }

    private Predicate getCategoryPredicate(CategorySearch categorySearch, Root<Category> categoryRoot) {
        List<Predicate> predicateList = new ArrayList<>();
        if (Objects.nonNull(categorySearch.getCategoryName())){
            Predicate predicate = criteriaBuilder.like(categoryRoot.get("name"), "%" +categorySearch.getCategoryName() +"%");
            predicateList.add(predicate);
        }
        if (Objects.nonNull(categorySearch.getDescription())){
            Predicate predicate = criteriaBuilder.like(categoryRoot.get("description"), "%" +categorySearch.getDescription() +"%");
            predicateList.add(predicate);
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));

    }

    private void setSearchOrder(CategorySearchPage categorySearchPage, Root<Category> categoryRoot, CriteriaQuery<Category> criteriaQuery) {
        if(categorySearchPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(categoryRoot.get(categorySearchPage.getSortByName())));
        }
        criteriaQuery.orderBy(criteriaBuilder.desc(categoryRoot.get(categorySearchPage.getSortByName())));

    }
}
