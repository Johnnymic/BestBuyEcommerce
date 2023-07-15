package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.PickupCenter;
import com.bestbuy.ecommerce.domain.entity.State;
import com.bestbuy.ecommerce.domain.repository.PickupCenterRepository;
import com.bestbuy.ecommerce.domain.repository.StateRepository;
import com.bestbuy.ecommerce.dto.request.PickCenterRequest;
import com.bestbuy.ecommerce.dto.responses.PickUpCenterResponse;
import com.bestbuy.ecommerce.exceptions.CenterNotFoundException;
import com.bestbuy.ecommerce.exceptions.StateNotFoundException;
import com.bestbuy.ecommerce.service.PickupCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PickupCenterServiceImpl implements PickupCenterService {
    private final PickupCenterRepository pickupCenterRepository;

    private final StateRepository stateRepository;


    @Override
    public PickUpCenterResponse addNewPickupCenter(PickCenterRequest pickCenterRequest) {
        PickupCenter pickupCenter =mapToEntity(pickCenterRequest);
          State state = stateRepository.findById(pickCenterRequest.getStateId())
                  .orElseThrow(()->new StateNotFoundException("STATE NOT FOUND"));

          pickupCenter.setState(state);

          if(state==null){
              throw  new StateNotFoundException("state not found");
          }
          state.setPickupCenters(Set.of((pickupCenter)));

          var newCenter = pickupCenterRepository.save(pickupCenter);
        return mapToResponse(newCenter);
    }

    @Override
    public List<PickUpCenterResponse> findCenterByStateName(String stateName) {
        return         pickupCenterRepository.findAll()
                .parallelStream()
                .filter(pickupCenter -> pickupCenter.getState().getName().equalsIgnoreCase(stateName))
                .map(this::mapToResponse).collect(Collectors.toList());

    }

    @Override
    public PickUpCenterResponse updatePickupCenter(Long centerId, PickCenterRequest pickCenterRequest) {
        PickupCenter pickupCenter= pickupCenterRepository.findById(centerId)
                .orElseThrow(()->new CenterNotFoundException("center not found"));
        pickupCenter.setName(pickupCenter.getName());
        pickupCenter.setPhone(pickupCenter.getPhone());
        pickupCenter.setEmail(pickupCenter.getEmail());
        pickupCenter.setAddress(pickupCenter.getAddress());
        pickupCenter.setDelivery(pickupCenter.getDelivery());
        pickupCenterRepository.save(pickupCenter);
        return  mapToResponse(pickupCenter);
    }

    @Override
    public String deleteCenter(Long centerId) {
        PickupCenter pickupCenter= pickupCenterRepository.findById(centerId)
                .orElseThrow(()->new CenterNotFoundException("center not found"));
         pickupCenterRepository.delete(pickupCenter);
        return "center for pickup is successfully deleted";
    }

    @Override
    public Page<PickUpCenterResponse> viewAllCenter(Integer pageNo, Integer pageSize, String sortBy) {
        List<PickupCenter> pickupCenters = pickupCenterRepository.findAll();
        List<PickUpCenterResponse> centerResponseList = pickupCenters.stream()
                .map(center-> PickUpCenterResponse.builder()
                        .location(center.getAddress())
                        .name(center.getName())
                        .email(center.getEmail())
                        .phone(center.getPhone())
                        .delivery(center.getDelivery())
                        .build()
                ).collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC , sortBy);
        int minimum = pageNo*pageSize;
        int maximum =Math.min (pageSize*(pageNo+1),centerResponseList.size());
        return  new PageImpl<>(centerResponseList.subList(minimum,maximum),pageRequest,centerResponseList.size());

    }


    private PickUpCenterResponse mapToResponse(PickupCenter newCenter) {
        return PickUpCenterResponse.builder()
                .name(newCenter.getName())
                .email(newCenter.getEmail())
                .address(newCenter.getAddress())
                .phone(newCenter.getPhone())
                .delivery(newCenter.getDelivery())
                .build();
    }


    private PickupCenter mapToEntity(PickCenterRequest pickCenterRequest ) {
        return  PickupCenter.builder()
                .name(pickCenterRequest.getName())
                .phone(pickCenterRequest.getPhone())
                .email(pickCenterRequest.getEmail())
                .address(pickCenterRequest.getLocation())
                .delivery(pickCenterRequest.getDelivery())

                .build();
    }


}
