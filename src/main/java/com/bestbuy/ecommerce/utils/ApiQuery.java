package com.bestbuy.ecommerce.utils;

import lombok.*;
import org.hibernate.annotations.Bag;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor

@Builder
public class ApiQuery {

    private HashMap<String , Object> objectHashMap;

    public ApiQuery() {
        this.objectHashMap = new HashMap<>();
    }


    public HashMap<String, Object> getParams() {
        return  this.objectHashMap;
    }

    public void putParams(String key, Object value) {
        this.objectHashMap = objectHashMap;
    }


}
