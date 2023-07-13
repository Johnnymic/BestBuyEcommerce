package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.domain.entity.PickupCenter;
import com.bestbuy.ecommerce.dto.request.PickCenterRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.PickUpCenterResponse;
import com.bestbuy.ecommerce.service.PickupCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pickupCenter")
@RequiredArgsConstructor
public class PickupCenterController {

    private final PickupCenterService pickupCenterService;

    @PostMapping("/create/new/center")
    public ResponseEntity<ApiResponse<PickUpCenterResponse>> addNewPickUpCenter(@RequestBody PickCenterRequest pickCenterRequest) {
        ApiResponse<PickUpCenterResponse> apiResponse = new ApiResponse<>(pickupCenterService.addNewPickupCenter(pickCenterRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view/pickup/center")
    public ResponseEntity<ApiResponse<List<PickUpCenterResponse>>> getPickupCenterByStateName(String name) {
        ApiResponse<List<PickUpCenterResponse>> apiResponse = new ApiResponse<>(pickupCenterService.findCenterByStateName(name));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/edit/pickup/center/{stateId}")
    public ResponseEntity<ApiResponse<PickUpCenterResponse>> editPickupCenter(@PathVariable("stateId") Long centerId, @RequestBody PickCenterRequest pickCenterRequest) {
        ApiResponse<PickUpCenterResponse> apiResponse = new ApiResponse<>(pickupCenterService.updatePickupCenter(centerId, pickCenterRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/pickup/{stateId}")
    public ResponseEntity<ApiResponse<String>> editPickupCenter(@PathVariable("stateId") Long centerId) {
        ApiResponse<String> apiResponse = new ApiResponse<>(pickupCenterService.deleteCenter(centerId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/fetch/all/centers/")
    public ResponseEntity<ApiResponse<Page<PickUpCenterResponse>>> viewAllCenter(@RequestParam(defaultValue = "page") Integer pageNo,
                                                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                 @RequestParam(defaultValue = "id") String sortBy) {


        ApiResponse<Page<PickUpCenterResponse>> apiResponse = new ApiResponse<>(pickupCenterService.viewAllCenter(pageNo, pageSize, sortBy));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }



}



