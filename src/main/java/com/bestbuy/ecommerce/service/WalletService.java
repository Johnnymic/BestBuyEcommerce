package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.WalletRequest;
import com.bestbuy.ecommerce.dto.responses.WalletResponse;

public interface WalletService {

   WalletResponse fundCustomerWallet(WalletRequest walletRequest);
}
