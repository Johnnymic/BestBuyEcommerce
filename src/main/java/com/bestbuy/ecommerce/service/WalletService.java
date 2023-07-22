package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.TransactionResponse;
import com.bestbuy.ecommerce.dto.request.WalletRequest;
import com.bestbuy.ecommerce.dto.responses.WalletBalanceResponse;
import com.bestbuy.ecommerce.dto.responses.WalletInfoResponse;
import com.bestbuy.ecommerce.dto.responses.WalletResponse;
import org.springframework.data.domain.Page;

public interface WalletService {

   WalletResponse fundCustomerWallet(WalletRequest walletRequest);

   WalletBalanceResponse getCustomerBalance();

   WalletInfoResponse viewCustomerWallet();

   Page<WalletResponse> viewCustomerWalletByPagination(Integer pageNo, Integer pageSize, String sortBy);

   Page<TransactionResponse>viewAllCustomerTransaction(Integer pageNo, Integer pageSize, String sortBy);
}
