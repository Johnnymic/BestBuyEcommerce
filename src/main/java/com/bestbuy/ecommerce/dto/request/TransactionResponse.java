package com.bestbuy.ecommerce.dto.request;

import com.bestbuy.ecommerce.enums.Channels;
import com.bestbuy.ecommerce.enums.PayStackBearer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private Long id;
    private String date;
    private String time;
    private String amount;
    private String purpose;
    private String reference;
    private String status;

}
