package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Amount;
import com.bestbuy.ecommerce.domain.entity.PayStackTransactions;
import com.bestbuy.ecommerce.domain.entity.Wallet;
import com.bestbuy.ecommerce.domain.repository.PaymentTransactionRepository;
import com.bestbuy.ecommerce.domain.repository.TransactionRepository;
import com.bestbuy.ecommerce.domain.repository.WalletRepository;
import com.bestbuy.ecommerce.dto.request.InitializePaymentRequest;
import com.bestbuy.ecommerce.dto.request.TransactionRequest;
import com.bestbuy.ecommerce.dto.request.WalletRequest;
import com.bestbuy.ecommerce.dto.responses.InitializePaymentResponse;
import com.bestbuy.ecommerce.exceptions.PaymentNotVerifiedException;
import com.bestbuy.ecommerce.exceptions.TrasactionNotIntializedException;
import com.bestbuy.ecommerce.service.TransactionService;
import com.bestbuy.ecommerce.service.WalletService;
import com.bestbuy.ecommerce.utils.ApiConnect;
import com.bestbuy.ecommerce.utils.ApiQuery;
import com.bestbuy.ecommerce.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl  implements TransactionService {
     private final HttpServletRequest httpServlet;

     private WalletService walletService;

     private ApiConnect apiConnection;

     private  final PaymentTransactionRepository paymentTransactionRepository;
//
//     private  final TransactionRepository transactionRepository;


    @Override
    public JSONObject initializePayment(Amount amount) {
        String email = UserUtils.getUserEmailFromContext();

        String amountInKobo = amount.getAmount() + "00";

        InitializePaymentRequest  request = new InitializePaymentRequest();
        request.setAmount(amountInKobo);
        request.setEmail(email);
        request.setReference(UUID.randomUUID().toString());
        request.setCallback_url("http://" + httpServlet.getServerName() + ":300" + "/confirm-payment?" + request.getReference());

        apiConnection = new ApiConnect("https://api.paystack.co/transaction/initialize");
        ApiQuery apiQuery = new ApiQuery();
        apiQuery.putParams("amount", request.getAmount());
        apiQuery.putParams("email", request.getEmail());
        apiQuery.putParams("reference", request.getReference());
        apiQuery.putParams("callback_url", request.getCallback_url());

        PayStackTransactions payStackTransactions = PayStackTransactions.builder()
                .reference(request.getReference())
                .success(true)
                .email(request.getEmail())
                .build();
        paymentTransactionRepository.save(payStackTransactions);


        return apiConnection.connectAndQuery(apiQuery);
      }

    @Override
    public InitializePaymentResponse verifyPayment(String reference) {
        InitializePaymentResponse responseDto = null;
        try {
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet("https://api.paystack.co/transaction/verify/" + reference);
            httpGet.addHeader("Content-type", "application/json");
            httpGet.addHeader("Authorization", "Bearer " + apiConnection.getAPI_KEY());
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            StringBuilder result = new StringBuilder();
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                BufferedReader bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

                String line ;
                while((line=bf.readLine()) != null ){
                      result.append(line);
                }



            }else{
                throw new Exception("Error Occured while connecting to paystack url");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            responseDto = objectMapper.readValue(result.toString(), InitializePaymentResponse.class);

            if(responseDto== null || responseDto.getStatus().equals("false")){
                throw  new PaymentNotVerifiedException("An error has occurred while verified payment");
            }
            else if(responseDto.getData().getStatus().equals("successs")){
                  responseDto.setStatus(true);
                  responseDto.setMessage("payment successful");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDto;
    }

    @Override
    public Object finalizeTransaction(String reference) {
        InitializePaymentResponse initializePaymentResponse = verifyPayment(reference);
        if(initializePaymentResponse.getStatus()) {
            PayStackTransactions transactionDetail = paymentTransactionRepository.findByReference(reference)
                    .orElseThrow(() -> new TrasactionNotIntializedException("transaction is not in record"));

            walletService.fundCustomerWallet(
                    WalletRequest
                            .builder()
                            .email(transactionDetail.getEmail())
                            .amount(initializePaymentResponse.getData().getAmount().divide(new BigDecimal(100)))
                            .build()
            );
            transactionDetail.setSuccess(true);
            paymentTransactionRepository.save(transactionDetail);
            return "payment is successful";
        }

        return  "transaction is not  successful";
    }
}