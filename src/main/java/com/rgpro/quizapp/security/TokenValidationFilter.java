package com.rgpro.quizapp.security;

import com.rgpro.quizapp.model.User;
import com.rgpro.quizapp.services.UserDetailsProviderService;
import com.rgpro.quizapp.services.UserTokenProviderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@AllArgsConstructor
public class TokenValidationFilter extends OncePerRequestFilter {
    private UserTokenProviderService userTokenValidatorService ;
    private UserDetailsProviderService userDetailsProviderService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = header.split(" ")[1].trim();
        var id = userTokenValidatorService.isValidToken(token);
        if(id == 0L || userTokenValidatorService.isExpiredToken(id)){
            filterChain.doFilter(request, response);
            return;
        }
        User userDetails = userDetailsProviderService.loadUserByUserId(id);
        UserAuthenticationToken authentication =
                new UserAuthenticationToken(userDetails.getId(), userDetails.getUsername(), token, AuthorityUtils.createAuthorityList("User"));
        authentication.setAuthenticated(true);
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
