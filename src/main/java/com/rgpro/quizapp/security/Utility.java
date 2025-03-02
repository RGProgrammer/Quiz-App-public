package com.rgpro.quizapp.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class Utility {
    public static UserAuthenticationToken getUserAuthenticationToken()
    {
        var authentication = SecurityContextHolder.getContext().getAuthentication() ;
        if(authentication instanceof UserAuthenticationToken )
        {
            return (UserAuthenticationToken)authentication;
        }
        return null ;
    }
}
