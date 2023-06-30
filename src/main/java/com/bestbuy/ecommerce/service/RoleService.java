package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.RoleRequest;
import com.bestbuy.ecommerce.dto.responses.RoleResponse;

public interface RoleService {
    RoleResponse addRoles(RoleRequest roleRequest);
}
