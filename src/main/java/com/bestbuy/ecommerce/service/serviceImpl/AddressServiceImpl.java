package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Address;
import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.State;
import com.bestbuy.ecommerce.domain.repository.AddressRepository;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.StateRepository;
import com.bestbuy.ecommerce.dto.request.AddressRequest;
import com.bestbuy.ecommerce.dto.responses.AddressResponse;
import com.bestbuy.ecommerce.exceptions.AddressNotFoundException;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.StateNotFoundException;
import com.bestbuy.ecommerce.service.AddressServices;
import com.bestbuy.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AddressServiceImpl implements AddressServices {

    private final AddressRepository addressRepository;

    private final StateRepository stateRepository;

    private final AppUserRepository appUserRepository;
    @Override
    public AddressResponse addNewAddress(AddressRequest addressRequest) {
        AppUser appUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("user not found"));

        State state = stateRepository.findById(addressRequest.getStateId())
                .orElseThrow(()-> new StateNotFoundException("state not found"));
         Address address = mapToAddress(addressRequest,appUser);
         address.setState(state);
         appUser.setAddress(address);
         appUserRepository.save(appUser);

         var newAddress = addressRepository.save(address);

         return mapToAddressResponse(newAddress);

    }

    @Override
    public AddressResponse viewAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new AddressNotFoundException("Address not found "));

        return  mapToAddressResponse(address);
    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressRequest addressRequest) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new AddressNotFoundException("Address not found "));
        address.setCountry(address.getCountry());
        address.setFullName(addressRequest.getFullName());
        address.setEmailAddress(addressRequest.getEmailAddress());
        address.setPhone(addressRequest.getPhone());
        address.setStreet(addressRequest.getStreet());
        addressRepository.save(address);
        return mapToAddressResponse(address);
    }

    @Override
    public List<AddressResponse> viewAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(this::mapToAddressResponse).collect(Collectors.toList());
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new AddressNotFoundException("Address not found "));
        addressRepository.delete(address);
        return " Address successfully , deleted";
    }


    private AddressResponse mapToAddressResponse(Address newAddress) {
        return AddressResponse.builder()

                .fullName(newAddress.getFullName())
                .phone(newAddress.getPhone())
                .emailAddress(newAddress.getEmailAddress())
                .street(newAddress.getStreet())
                .state(State.builder()
                        .id(newAddress.getState().getId())
                        .name(newAddress.getState().getName())
                        .build())
                .country(newAddress.getCountry())
                .build();
    }

    private Address mapToAddress(AddressRequest addressRequest,AppUser appUser) {
        return Address.builder()
                .fullName(addressRequest.getFullName())
                .phone(addressRequest.getPhone())
                .emailAddress(addressRequest.getEmailAddress())
                .street(addressRequest.getStreet())
                .country(addressRequest.getCountry())
                .user(appUser)
                .build();



    }
}
