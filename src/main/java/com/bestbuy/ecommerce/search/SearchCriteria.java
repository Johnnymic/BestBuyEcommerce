package com.bestbuy.ecommerce.search;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria {

    private String filterOperator;

    private Object value;

    private String Operation;

    private String dataOption;

    public SearchCriteria(String filterOperator, Object value, String operation) {
        this.filterOperator = filterOperator;
        this.value = value;
        Operation = operation;

    }
}
