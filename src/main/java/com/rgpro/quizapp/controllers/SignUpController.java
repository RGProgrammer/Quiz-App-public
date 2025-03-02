package com.rgpro.quizapp.controllers;

import com.rgpro.quizapp.common.exceptions.EmailAlreadyUsedException;
import com.rgpro.quizapp.common.exceptions.UsernameAlreadyExistsException;
import com.rgpro.quizapp.common.messages.ApiResponse;
import com.rgpro.quizapp.common.messages.ErrorCode;
import com.rgpro.quizapp.dto.UserAuthorization;
import com.rgpro.quizapp.model.User;
import com.rgpro.quizapp.services.UserSignUpService;
import com.rgpro.quizapp.services.UserTokenProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class SignUpController {
    private final UserSignUpService userSignUpService ;
    private final BCryptPasswordEncoder passwordEncoder ;
    private final UserTokenProviderService userTokenProviderService ;

    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse<UserAuthorization>> signUp(@RequestParam String username,
                                              @RequestParam String email,
                                              @RequestParam String password)
    {
       try{
           User user = userSignUpService.addNewUser(
                   new User(username,
                           passwordEncoder.encode(password),
                           email));
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
       }catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.ok(new ApiResponse<>(
                  null,
                    ErrorCode.USERNAME_TAKEN.getCode()));
       }catch (EmailAlreadyUsedException e){
           return ResponseEntity.ok(new ApiResponse<>(
                   null,
                   ErrorCode.EMAIL_ALREADY_USED.getCode()));
       }
    }
}
