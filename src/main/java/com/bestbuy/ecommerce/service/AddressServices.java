package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.AddressRequest;
import com.bestbuy.ecommerce.dto.responses.AddressResponse;

import java.util.List;

public interface AddressServices {
    AddressResponse addNewAddress(AddressRequest addressRequest,Long stateId);

    AddressResponse viewAddress(Long addressId);

    AddressResponse updateAddress(Long addressId, AddressRequest addressRequest);

    List<  AddressResponse> viewAllAddresses();

    String deleteAddress(Long addressId);
}
