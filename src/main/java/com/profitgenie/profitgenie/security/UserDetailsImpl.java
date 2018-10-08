package com.profitgenie.profitgenie.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.profitgenie.profitgenie.dao.domain.User;
import org.springframework.util.Assert;

public class UserDetailsImpl implements UserDetails, Serializable {


    private User user;
    private String sessionId;


    public UserDetailsImpl(User user, String sessionId) {
        Assert.notNull(user, "User must not be null");
        Assert.notNull(sessionId, "Session must not be null");
        this.user = user;
        this.sessionId = sessionId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
