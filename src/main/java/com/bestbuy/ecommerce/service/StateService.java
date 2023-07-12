package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.StateRequest;
import com.bestbuy.ecommerce.dto.responses.StateResponse;

import java.util.List;

public interface StateService {
    StateResponse addNewState(StateRequest stateRequest);

    List<StateResponse> viewAllState();

    StateResponse viewState(Long stateId);

    StateResponse editState(StateRequest request, Long stateId);

    String deleteState(Long stateId);
}

