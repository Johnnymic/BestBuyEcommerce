package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.AddNewProductRequest;

import com.bestbuy.ecommerce.dto.request.AdminRequest;
import com.bestbuy.ecommerce.dto.request.UpdateProductRequest;
import com.bestbuy.ecommerce.dto.responses.*;
import com.bestbuy.ecommerce.dto.request.RoleRequest;
import com.bestbuy.ecommerce.service.AdminService;
import com.bestbuy.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private  final RoleService roleService;

    private  final AdminService adminService;




     @PostMapping("/add/roles/")
     public ResponseEntity<ApiResponse<RoleResponse>>addRole(@RequestBody RoleRequest roleRequest){
         ApiResponse<RoleResponse> apiResponse = new ApiResponse<>(roleService.addRoles(roleRequest));
         return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
     }

     @PostMapping("/add/permission/")
     public ResponseEntity<ApiResponse<RoleResponse>>addPermissions(@RequestBody AddPermissionRequest permissionRequest, Long id){
         ApiResponse<RoleResponse>apiResponse = new ApiResponse<>(roleService.addRolesPermission(permissionRequest,id));
         return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
     }
     @PostMapping("/register")
     public ResponseEntity<ApiResponse<AdminResponse>>registerAdmin(@RequestBody AdminRequest adminRequest){
         ApiResponse<AdminResponse>apiResponse = new ApiResponse<>(roleService.registerAdmin(adminRequest));
         return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
     }

     @GetMapping("/deActive/user/{userId}")
     public ResponseEntity<ApiResponse<String>>deActiveCustomer(@PathVariable("userId") Long userId){
         ApiResponse<String> apiResponse = new ApiResponse<>(roleService.deActiveUser(userId));
         return new ResponseEntity<>(apiResponse, HttpStatus.OK);

     }


     @PostMapping("/add/new/products")
    public ResponseEntity<ApiResponse<ProductResponse>>addNewProduct(@RequestBody AddNewProductRequest addNewProductRequest){
         ApiResponse<ProductResponse> apiResponse= new ApiResponse<>(adminService.addNewProduct(addNewProductRequest));
         return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
     }

     @GetMapping ("/view/single/product/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>>fetchSingleProduct(@PathVariable("productId") Long productId){
         ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(adminService.fetchSingleProduct(productId));
         return new ResponseEntity<>(apiResponse,HttpStatus.OK);

     }
     @PutMapping("/update/product/{productId}")
     public ResponseEntity<ApiResponse<ProductResponse>>updateProduct(@PathVariable("productId") Long productId, UpdateProductRequest updateProduct){
         ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(adminService.updateProduct(productId,updateProduct));
         return new ResponseEntity<>(apiResponse,HttpStatus.OK);

     }
     @DeleteMapping("/delete/product/{productId}")
    public ResponseEntity<ApiResponse<String>>deleteProduct(@PathVariable("productId") Long productId){
         ApiResponse<String>apiResponse = new ApiResponse<>(adminService.deleteProduct(productId));
         return new ResponseEntity<>(apiResponse,HttpStatus.OK);

     }




}
