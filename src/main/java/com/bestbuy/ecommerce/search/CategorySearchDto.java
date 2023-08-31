package com.bestbuy.ecommerce.search;

import com.bestbuy.ecommerce.domain.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategorySearchDto {

    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    List<Category> searchCategoryCriteria(CategorySearchPage categorySearchPage , CategorySearch categorySearch){
        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);

        Predicate predicate = getCategoryPredicate(categorySearch, categoryRoot);
        criteriaQuery.where(predicate);
        setSearchOrder(categorySearchPage,categoryRoot,criteriaQuery);
        TypedQuery<Category> categoryTypedQuery = entityManager.createQuery(criteriaQuery);
        categoryTypedQuery.setFirstResult(categorySearchPage.getPageNo()* categorySearchPage.getPageSize());
        categoryTypedQuery.setMaxResults(categorySearchPage.getPageSize());

        Pageable pageable =getPageRequest(categorySearch);


        return null;

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
