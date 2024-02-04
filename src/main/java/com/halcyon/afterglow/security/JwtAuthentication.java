package com.halcyon.afterglow.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthentication implements Authentication {
    private boolean authenticated;
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Object getCredentials() {
        return email;
    }

    @Override
    public Object getDetails() {
        return email;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public String getName() {
        return email;
    }
}