package com.safepass.safebuilding.common.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.common.jwt.entity.response.TokenResponse;
import com.safepass.safebuilding.common.meta.AdminStatus;
import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.common.meta.LoginAuthorities;
import com.safepass.safebuilding.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserPrinciple implements UserDetails  {
    private UUID id;
    @JsonIgnore
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private CustomerStatus customerStatus;
    private AdminStatus adminStatus;
    private TokenResponse tokenResponse;
    private Collection<? extends GrantedAuthority> authorities;
    private String accessToken;
    private String refreshToken;

    public static UserPrinciple adminBuild(Admin admin) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(LoginAuthorities.ADMIN.toString()));
        return UserPrinciple.builder()
                .id(admin.getId())
                .fullname(admin.getFullname())
                .phone(admin.getPhone())
                .email(admin.getEmail())
                .password(admin.getPassword())
                .adminStatus(admin.getStatus())
                .authorities(authorities)
                .build();
    }

    public static UserPrinciple customerBuild(Customer customer) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(LoginAuthorities.CUSTOMER.toString()));
        return UserPrinciple.builder()
                .id(customer.getId())
                .fullname(customer.getFullname())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .password(customer.getPassword())
                .customerStatus(customer.getStatus())
                .authorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
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
