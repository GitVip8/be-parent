package com.be.auth;

import com.be.common.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetail implements UserDetails {

    private User user;

    UserDetail(User user) {
        this.user = user;
    }

    private UserDetail() {
    }


    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.user == null || this.user.getRoles() == null) return null;
        return this.user.getRoles().stream().map(a -> (GrantedAuthority) a::getName).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user == null ? null : this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user == null ? null : this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
