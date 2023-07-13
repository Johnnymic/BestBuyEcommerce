package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.PickCenterRequest;
import com.bestbuy.ecommerce.dto.responses.PickUpCenterResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PickupCenterService {
    PickUpCenterResponse addNewPickupCenter(PickCenterRequest pickCenterRequest);

    List<PickUpCenterResponse> findCenterByStateName(String name);

    PickUpCenterResponse updatePickupCenter(Long centerId, PickCenterRequest pickCenterRequest);

    String  deleteCenter(Long centerId);

    Page<PickUpCenterResponse> viewAllCenter(Integer pageNo, Integer pageSize, String sortBy);
}
