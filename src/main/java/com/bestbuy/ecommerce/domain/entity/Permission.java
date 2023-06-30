package com.bestbuy.ecommerce.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    public static final String ADMIN_CREATE = "ADMIN_CREATE";
    public static final String ADMIN_READ = "ADMIN_READ";
    public static final String ADMIN_UPDATE = "ADMIN_UPDATE";

    public static final String ADMIN_DELETE = "ADMIN_DELETE";

    private String permission;



}
