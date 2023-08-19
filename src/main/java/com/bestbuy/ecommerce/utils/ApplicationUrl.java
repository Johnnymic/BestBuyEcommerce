package com.bestbuy.ecommerce.utils;

import javax.print.DocFlavor;

public interface ApplicationUrl {

    String BASE_API_URL = "/api/v1/";

    String NEW_ADDRESS = "address/new/address/";

    String VIEW_ADDRESS = "address/view/address/{addressId}";

    String UPDATE_ADDRESS = "address/update/address/{addressId}";

    String DELETE_ADDRESS = "address/delete/address/{addressId}";

    String VIEW_ALL_ADDRESS = "address/update/address/{addressId}";

}
