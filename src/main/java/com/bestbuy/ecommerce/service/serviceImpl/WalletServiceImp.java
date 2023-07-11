package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Transactions;
import com.bestbuy.ecommerce.domain.entity.Wallet;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.TransactionRepository;
import com.bestbuy.ecommerce.domain.repository.WalletRepository;
import com.bestbuy.ecommerce.dto.request.WalletRequest;
import com.bestbuy.ecommerce.dto.responses.WalletResponse;
import com.bestbuy.ecommerce.enums.PaymentPurpose;
import com.bestbuy.ecommerce.enums.TransactionStatus;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.CustomerWalletNotFoundException;
import com.bestbuy.ecommerce.service.WalletService;
import com.bestbuy.ecommerce.utitls.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WalletServiceImp implements WalletService {

    private final WalletRepository walletRepository;

    private final AppUserRepository appUserRepository;

    private final TransactionRepository transactionRepository;


    @Override
    public WalletResponse fundCustomerWallet(WalletRequest walletRequest) {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("Please login in"));
         Wallet wallet = loginUser.getWallet();
         if(wallet== null){
             throw new CustomerWalletNotFoundException("customer wallet is empty");
         }
         wallet.setAccountBalance(wallet.getAccountBalance().add(walletRequest.getAmount()));
         walletRepository.save(wallet);

        Transactions transactions = Transactions.builder()
                .wallet(wallet)
                .reference(UUID.randomUUID().toString())
                .Amount(String.valueOf(wallet.getAccountBalance()))
                .paymentPurpose(PaymentPurpose.DEPOSIT)
                .status(TransactionStatus.COMPLETE)
                .build();

        transactionRepository.save(transactions);
        return mapToResponse(walletRequest,loginUser,wallet);
    }

    private WalletResponse mapToResponse(WalletRequest walletRequest,AppUser appUser, Wallet wallet) {
       return  WalletResponse.builder()
                .fullName(appUser.getFirstName() + ":" + appUser.getLastName())
                .newBalance(walletRequest.getAmount())
                .depositAmount(wallet.getAccountBalance())
               .build();

    }
}
