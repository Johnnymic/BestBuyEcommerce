package com.bestbuy.ecommerce.domain.entity;

import com.bestbuy.ecommerce.enums.PaymentPurpose;
import com.bestbuy.ecommerce.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

public class Transactions  extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String Amount ;

    private String reference;

    @Enumerated(EnumType.STRING)
    private PaymentPurpose paymentPurpose;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private Wallet wallet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

}
