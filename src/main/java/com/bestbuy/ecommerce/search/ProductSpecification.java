package com.bestbuy.ecommerce.search;

import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.entity.SearchCriteria;
import com.bestbuy.ecommerce.domain.entity.SubCategory;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private final SearchCriteria searchCriteria;


    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String strToSearch  =  searchCriteria.getValue().toString();
        switch(Objects.requireNonNull(SearchOperator.getSimpleOperation(searchCriteria.getOperation()))){
            case CONTAINS :
                if(searchCriteria.getFilterOperator().equals("productName")){
                    return criteriaBuilder.like(criteriaBuilder.lower(subCategoryJoin(root)
                            .<String>get(searchCriteria.getFilterOperator())), "%" + strToSearch + "%" );

                }
                return criteriaBuilder.like(criteriaBuilder.
                        lower(root.get(searchCriteria.getFilterOperator())),"%" + strToSearch + "%" );

            case DOES_NOT_CONTAIN:
                if(searchCriteria.getFilterOperator().equals("productName")){
                    return criteriaBuilder.notLike(criteriaBuilder.lower(subCategoryJoin(root)
                            .<String>get(searchCriteria.getFilterOperator())), "%" + strToSearch + "%");
                }
                return  criteriaBuilder.like(criteriaBuilder
                        .lower(root.get(searchCriteria.getFilterOperator())),"%" + strToSearch + "%" );

            case BEGINS_WITH:
                if(searchCriteria.getFilterOperator().equals("ProductName")){
                        return criteriaBuilder.like(criteriaBuilder.lower(subCategoryJoin(root)
                                .<String>get(searchCriteria.getFilterOperator())),"%" + strToSearch + "%");
                   }
                return criteriaBuilder.like(criteriaBuilder.lower(root.
                        get(searchCriteria.getFilterOperator())), "%" + strToSearch + "%");
            case DOES_NOT_BEGIN_WITH:
                if(searchCriteria.getFilterOperator().equals("productName")){
                   return criteriaBuilder.notLike(criteriaBuilder.lower(subCategoryJoin(root)
                             . get(searchCriteria.getFilterOperator())),"%" + strToSearch + "%");
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.
                        get(searchCriteria.getFilterOperator())), "%" + strToSearch + "%");
            case ENDS_WITH:
                if(searchCriteria.getFilterOperator().equals("productName")){
                    criteriaBuilder.like(criteriaBuilder.lower(subCategoryJoin(root)
                            . <String>get(searchCriteria.getFilterOperator())),"%" + strToSearch + "%");
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.
                        get(searchCriteria.getFilterOperator())), "%" + strToSearch + "%");
                }


               return null;


    }

    private Join<Product, SubCategory> subCategoryJoin(Root<Product> root) {
   return root.join("category");
    }
}
