package com.bestbuy.ecommerce.search;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria {

    private String filterKey;

    private Object value;

    private String Operation;

    private String dataOption;

    public SearchCriteria(String filterOperator, Object value, String operation) {
        this.filterKey = filterOperator;
        this.value = value;
        Operation = operation;

    }
}
