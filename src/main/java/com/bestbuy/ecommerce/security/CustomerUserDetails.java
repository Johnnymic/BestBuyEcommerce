package com.bestbuy.ecommerce.security;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomerUserDetails implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser =appUserRepository.findByEmail(email)
                .orElseThrow(()-> new AppUserNotFountException(email));
        return new User(appUser.getEmail(), appUser.getPassword(),  getAuthority(appUser));
    }

    private Collection<? extends GrantedAuthority> getAuthority(AppUser appUser) {
        return  Collections.singleton(new SimpleGrantedAuthority(appUser.getRoles().name()));
    }
}
