package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.StateRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.StateResponse;
import com.bestbuy.ecommerce.service.StateService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/state")
public class StateController {

    private final StateService stateService;

    @PostMapping("/create/new/state/")
    public ResponseEntity<ApiResponse<StateResponse>> addNewState(@RequestBody StateRequest stateRequest){

        ApiResponse<StateResponse> apiResponse = new ApiResponse<>(stateService.addNewState(stateRequest));
        return  new ResponseEntity<>(apiResponse , HttpStatus.CREATED);
    }

    @GetMapping("/view/all/state")
    public ResponseEntity<ApiResponse<List<StateResponse>>> viewAllState(){

        ApiResponse<List<StateResponse>> apiResponse = new ApiResponse<>(stateService.viewAllState());
        return  new ResponseEntity<>(apiResponse , HttpStatus.OK);
    }

    @GetMapping("/view/state/{stateId}")
    public ResponseEntity<ApiResponse<StateResponse>>viewAllState(@PathVariable("stateId") Long stateId){

        ApiResponse<StateResponse> apiResponse = new ApiResponse<>(stateService.viewState(stateId));
        return  new ResponseEntity<>(apiResponse , HttpStatus.OK);
    }

    @PutMapping("/edit/state/{stateId}")
    public ResponseEntity<ApiResponse<StateResponse>>updateState(@RequestBody StateRequest request, @PathVariable("stateId") Long stateId){

        ApiResponse<StateResponse> apiResponse = new ApiResponse<>(stateService.editState( request,stateId));
        return  new ResponseEntity<>(apiResponse , HttpStatus.OK);
    }

    @DeleteMapping("/delete/state/{stateId}")
    public ResponseEntity<ApiResponse<String>>updateState( @PathVariable("stateId") Long stateId){

        ApiResponse<String> apiResponse = new ApiResponse<>(stateService.deleteState(stateId));
        return  new ResponseEntity<>(apiResponse , HttpStatus.OK);
    }



}
