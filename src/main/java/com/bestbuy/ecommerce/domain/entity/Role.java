package com.bestbuy.ecommerce.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Role {
    private  Long roleId;
    private String roleName;

    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority>  authorities = getPermissions().stream()
                .map(permissions-> new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getRoleName()));
        return authorities;
    }

}
