package com.profitgenie.profitgenie.security;

import com.profitgenie.profitgenie.dao.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UserHasBecomeMember implements Authentication {


    private User principal;
    private Object credentials;
    private List<GrantedAuthority> updatedAuthorities;
    private boolean isAuthenticated;


    public UserHasBecomeMember(User principal, Object credentials, List<GrantedAuthority> updatedAuthorities) {
        this.principal = principal;
        this.credentials = credentials;
        this.updatedAuthorities = updatedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.updatedAuthorities;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return principal.getEmail();
    }
}
