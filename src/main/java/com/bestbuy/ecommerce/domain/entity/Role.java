package com.bestbuy.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long roleId;

    private String roleName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "permission")
    public List<String> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority>  authorities = getPermissions().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getRoleName()));
        return authorities;
    }
}
