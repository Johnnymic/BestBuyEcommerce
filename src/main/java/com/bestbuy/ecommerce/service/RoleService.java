package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.RoleRequest;
import com.bestbuy.ecommerce.dto.responses.AddPermissionRequest;
import com.bestbuy.ecommerce.dto.responses.RoleResponse;
import com.bestbuy.ecommerce.exceptions.RolesNotFoundException;

public interface RoleService {
    RoleResponse addRoles(RoleRequest roleRequest) throws RolesNotFoundException;



    RoleResponse addRolesPermission(AddPermissionRequest permissionRequest, Long id);
}
