package com.bestbuy.ecommerce.search;

import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.entity.SubCategory;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private final SearchCriteria searchCriteria;
    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String strToSearch  =  searchCriteria.getValue().toString();
        switch(Objects.requireNonNull(SearchOperator.getSimpleOperation(searchCriteria.getOperation()))){
            case CONTAINS :
                if(searchCriteria.getFilterKey().equals("productName")){
                    return criteriaBuilder.like(criteriaBuilder.lower(subCategoryJoin(root)
                            .<String>get(searchCriteria.getFilterKey())), "%" + strToSearch + "%" );

                }
                return criteriaBuilder.like(criteriaBuilder.
                        lower(root.get(searchCriteria.getFilterKey())),"%" + strToSearch + "%" );

            case DOES_NOT_CONTAIN:
                if(searchCriteria.getFilterKey().equals("productName")){
                    return criteriaBuilder.notLike(criteriaBuilder.lower(subCategoryJoin(root)
                            .<String>get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
                }
                return  criteriaBuilder.like(criteriaBuilder
                        .lower(root.get(searchCriteria.getFilterKey())),"%" + strToSearch + "%" );

            case BEGINS_WITH:
                if(searchCriteria.getFilterKey().equals("ProductName")){
                        return criteriaBuilder.like(criteriaBuilder.lower(subCategoryJoin(root)
                                .<String>get(searchCriteria.getFilterKey())),"%" + strToSearch + "%");
                   }
                return criteriaBuilder.like(criteriaBuilder.lower(root.
                        get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case DOES_NOT_BEGIN_WITH:
                if(searchCriteria.getFilterKey().equals("productName")){
                   return criteriaBuilder.notLike(criteriaBuilder.lower(subCategoryJoin(root)
                             . get(searchCriteria.getFilterKey())),"%" + strToSearch + "%");
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.
                        get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case ENDS_WITH:
                if(searchCriteria.getFilterKey().equals("productName")){
                    criteriaBuilder.like(criteriaBuilder.lower(subCategoryJoin(root)
                            . <String>get(searchCriteria.getFilterKey())),"%" + strToSearch + "%");
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.
                        get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");

            case DOES_NOT_END_WITH:
                if (searchCriteria.getFilterKey().equalsIgnoreCase("productName")){
                        criteriaBuilder.notLike(criteriaBuilder.lower(subCategoryJoin(root).get(searchCriteria.getFilterKey())),"%" + strToSearch + "%" );
                       }
                criteriaBuilder.notLike(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),"%" + strToSearch + "%");
            case EQUAL:
                if(searchCriteria.getFilterKey().equals("productName")){
                       System.out.println(searchCriteria.getValue());
                    return criteriaBuilder.equal(subCategoryJoin(root).
                            <String>get(searchCriteria.getFilterKey()), searchCriteria.getValue());
                }
                return criteriaBuilder.equal(root.get(searchCriteria.getFilterKey()),searchCriteria.getValue());
                case NOT_EQUAL:
                     if (searchCriteria.getFilterKey().equals("product")){
                         return criteriaBuilder.notEqual(subCategoryJoin(root).get(searchCriteria.getFilterKey()),searchCriteria.getValue());
                     }
                     criteriaBuilder.notEqual(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());
            case NUL:
                return criteriaBuilder.isNull(root.get(searchCriteria.getFilterKey()));
            case NOT_NULL:
                return criteriaBuilder.isNotNull(root.get(searchCriteria.getFilterKey()));

            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(searchCriteria.getFilterKey()),searchCriteria.getValue().toString());
            case GREATER_THAN_EQUAL:
                return  criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getFilterKey()),searchCriteria.getValue().toString());
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(searchCriteria.getFilterKey()),searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL:
                return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
                }
                return null;

    }
    private Join<Product, SubCategory> subCategoryJoin(Root<Product> root) {
   return root.join("category");
    }
}
