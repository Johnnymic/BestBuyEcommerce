package com.bestbuy.ecommerce.search;

import lombok.Data;
import org.springframework.data.domain.Sort;
@Data

public class CategorySearchPage {
    private int pageNo = 0;

    private  int pageSize = 5;

    private String sortByName = "name";

    private Sort.Direction sortDirection = Sort.Direction.ASC;
}
