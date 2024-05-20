package com.example.bookRental.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {


    private UserMember userMember;

    public UserPrincipal(UserMember userMember){
        this.userMember=userMember;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Roles> roles=userMember.getRole();
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for (Roles role:roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return userMember.getPassword();
    }

    @Override
    public String getUsername() {
        return userMember.getUserName();
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
