package com.bestbuy.ecommerce.domain.entity;

import com.bestbuy.ecommerce.enums.Gender;
import com.bestbuy.ecommerce.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AppUser extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @Column(name = "first_name", nullable = false)

    private String firstName;

    private String lastName;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String date_of_birth;

    private String phone;

    private String password;

    private Boolean isEnabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "appUser")
    private List<JwtToken> tokens = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    private Cart cart = new Cart();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="wishlist_id")
    private Wishlist wishlist;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
