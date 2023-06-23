package com.bestbuy.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private String accessToken;

    private String refreshToken;
    private boolean isExpired;
    private boolean isRevoked;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appUser_id" )
    private AppUser appUser;


}
