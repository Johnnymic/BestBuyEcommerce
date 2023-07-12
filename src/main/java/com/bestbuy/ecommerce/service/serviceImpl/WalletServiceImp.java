package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Transactions;
import com.bestbuy.ecommerce.domain.entity.Wallet;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.TransactionRepository;
import com.bestbuy.ecommerce.domain.repository.WalletRepository;
import com.bestbuy.ecommerce.dto.request.WalletRequest;
import com.bestbuy.ecommerce.dto.responses.WalletBalanceResponse;
import com.bestbuy.ecommerce.dto.responses.WalletInfoResponse;
import com.bestbuy.ecommerce.dto.responses.WalletResponse;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.CustomerWalletNotFoundException;
import com.bestbuy.ecommerce.service.WalletService;
import com.bestbuy.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bestbuy.ecommerce.enums.PaymentPurpose.DEPOSIT;
import static com.bestbuy.ecommerce.enums.TransactionStatus.COMPLETE;

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
                .paymentPurpose(DEPOSIT)
                .status(COMPLETE)
                .build();

        transactionRepository.save(transactions);
        return mapToResponse(walletRequest,loginUser,wallet);
    }

    @Override
    public WalletBalanceResponse getCustomerBalance() {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("Please login in"));
        Wallet wallet = loginUser.getWallet();
         BigDecimal  customerAccount  =  wallet.getAccountBalance();
        return WalletBalanceResponse.builder()
                .currentBalance(customerAccount)
                .build();
    }

    @Override
    public WalletInfoResponse viewCustomerWallet() {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("Please login in"));
        Wallet wallet = loginUser.getWallet();
         String currentInStringFormat=UserUtils.formatToLocale(wallet.getAccountBalance());
        DecimalFormatSymbols symbols =new DecimalFormatSymbols(new Locale("en", "NG"));
        symbols.setCurrencySymbol("N");

        return WalletInfoResponse.builder()
                .firstName(loginUser.getFirstName())
                .lastName(loginUser.getLastName())
                .email(loginUser.getEmail())
                .baseCurrency(currentInStringFormat)
                .walletBalance(String.valueOf(wallet.getAccountBalance()))
                .build();
    }

    @Override
    public Page<WalletResponse> viewCustomerWalletByPagination(Integer pageNo, Integer pageSize, String sortBy) {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletResponse> walletResponses = wallets.stream()
                .map(wallet ->WalletResponse.builder()
                        .fullName(wallet.getAppUser().getFirstName() + ":" + wallet.getAppUser().getLastName())
                        .depositAmount(wallet.getAccountBalance())
                        .build()).collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(pageSize,pageSize , Sort.Direction.DESC,sortBy);
        int minimum= pageNo*pageSize;
        int maximum = Math.min(pageSize* (pageNo+1),walletResponses.size());
        return new PageImpl<>(walletResponses.subList(minimum,maximum),pageRequest,walletResponses.size());
    }




    private WalletResponse mapToResponse(WalletRequest walletRequest,AppUser appUser, Wallet wallet) {
       return  WalletResponse.builder()
                .fullName(appUser.getFirstName() + ":" + appUser.getLastName())
                .newBalance(walletRequest.getAmount())
                .depositAmount(wallet.getAccountBalance())
               .build();

    }
}
