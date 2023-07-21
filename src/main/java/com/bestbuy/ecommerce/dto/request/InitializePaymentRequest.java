package com.bestbuy.ecommerce.dto.request;

import com.bestbuy.ecommerce.enums.Channels;
import com.bestbuy.ecommerce.enums.PayStackBearer;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitializePaymentRequest {
    private String amount;
    private String reference;
    private String email;
    private PayStackBearer paystackBearer = PayStackBearer.ACCOUNT;
    private String callback_url;
    private Integer invoice_limit;
    private Channels[] channels;
}
