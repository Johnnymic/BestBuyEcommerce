package com.bestbuy.ecommerce.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "state_tbl")
public class State {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;

   @JsonIgnore
   @OneToMany(mappedBy = "state" ,cascade = CascadeType.ALL)
   private Set<PickupCenter> pickupCenters;

}
