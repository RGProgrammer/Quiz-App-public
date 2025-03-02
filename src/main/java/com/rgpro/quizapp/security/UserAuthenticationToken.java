package com.rgpro.quizapp.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserAuthenticationToken extends AbstractAuthenticationToken {
    private final Long userId;
    private final String username ;
    private final String token ;
    public UserAuthenticationToken(Long userId,String username , String token,
                                   Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId =userId ;
        this.username = username ;
        this.token = token ;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    public Long getId(){
        return userId ;
    }
}
