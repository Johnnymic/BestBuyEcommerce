package com.bestbuy.ecommerce.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

public class ModeOfPayment  {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long modeOfPaymentId;
   private String paymentName;

    private String provider;

    @JsonIgnore
    @OneToOne(mappedBy = "modeOfPayment", cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
}
