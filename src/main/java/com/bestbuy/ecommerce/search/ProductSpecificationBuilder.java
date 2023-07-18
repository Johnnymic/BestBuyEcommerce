package com.bestbuy.ecommerce.search;

import com.bestbuy.ecommerce.domain.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecificationBuilder {

    private List<SearchCriteria> params;

    public ProductSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final  ProductSpecificationBuilder with(String key, Object value,String operation){
        params.add(new SearchCriteria(key,value,operation));
        return this;
    }
    public final  ProductSpecificationBuilder with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<Product> build(){
        if(params.size()== 0){
            return  null;
        }
        Specification<Product>  result = new ProductSpecification(params.get(0));
        for (int idx =1 ; idx< params.size() ; idx++){
            SearchCriteria criteria =params.get(idx);
            result= SearchOperator.getDataOption(criteria.getDataOption())==SearchOperator.ALL
                    ? Specification.where(result).and(new ProductSpecification(criteria))
                    :Specification.where(result).or(new ProductSpecification(criteria));

         }
        return  result;
      }



}
