package com.rgpro.quizapp.controllers;


import com.rgpro.quizapp.common.messages.ApiResponse;
import com.rgpro.quizapp.common.messages.ErrorCode;
import com.rgpro.quizapp.dto.UserAuthorization;
import com.rgpro.quizapp.model.User;
import com.rgpro.quizapp.security.UserAuthenticationToken;
import com.rgpro.quizapp.services.UserDetailsProviderService;
import com.rgpro.quizapp.services.UserTokenProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final UserDetailsProviderService userDetailsProviderService;
    private final UserTokenProviderService userTokenProviderService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<UserAuthorization>> Login(@RequestParam String username,
                                                                @RequestParam String password) {
        try {
            User user = userDetailsProviderService.loadUserByUsername(username);
            if (passwordEncoder.matches(password, user.getPassword())) {
                //TODO improve authentication security
                String token = UUID.randomUUID().toString();
                Long res = userTokenProviderService.addUserToken(user, token,
                        LocalDateTime.now(),LocalDateTime.now().plusDays(15));
                if (res.equals(user.getId())) {
                    return ResponseEntity.ok(new ApiResponse<>(
                            new UserAuthorization(username,token,null),
                            null));
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponse<>(
                                    null,
                                    ErrorCode.VALIDATION_ERROR.getCode()));
                }

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(
                                null,
                                ErrorCode.USERNAME_PASSWORD_INCORRECT.getCode()));
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.USER_NOT_FOUND.getCode()));
        }

    }

    @PostMapping(value = "/signout")
    public ResponseEntity<Object> Logout(@RequestParam(name = "all",defaultValue ="false") boolean allOut) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthenticationToken) {
            var user = userDetailsProviderService.loadUserByUsername(((UserAuthenticationToken) authentication).getPrincipal());
            var id = userTokenProviderService.isUserValidToken(user, ((UserAuthenticationToken) authentication).getCredentials());
            if (Objects.equals(id, user.getId())) {
                if (allOut) {
                    userTokenProviderService.removeAllTokensForUser(user);//TODO expire all user token
                } else {
                    userTokenProviderService.removeTokenForUser(user,
                            ((UserAuthenticationToken) authentication).getCredentials());//TODO expire current user token
                }
                return ResponseEntity.ok().build();
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        null,
                        ErrorCode.VALIDATION_ERROR.getCode()));

    }

}
