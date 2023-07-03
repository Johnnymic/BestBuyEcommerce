package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.request.RoleRequest;
import com.bestbuy.ecommerce.dto.responses.RoleResponse;
import com.bestbuy.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {

    private  RoleService roleService;

     @PostMapping("/add-roles")
     public ResponseEntity<ApiResponse<RoleResponse>>addRole(@RequestBody RoleRequest roleRequest){
         ApiResponse<RoleResponse> apiResponse = new ApiResponse<>(roleService.addRoles(roleRequest));
         return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
     }





}
