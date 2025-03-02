package com.rgpro.quizapp.services;

import com.rgpro.quizapp.common.exceptions.EmailAlreadyUsedException;
import com.rgpro.quizapp.common.exceptions.UsernameAlreadyExistsException;
import com.rgpro.quizapp.model.User;
import com.rgpro.quizapp.repositories.UserDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSignUpService {
    private UserDetailsRepository userDetailsRepository ;
    public boolean checkUsernameExists(String username) {
        var user = userDetailsRepository.findUsername(username ) ;
        return user.isPresent();
    }
    public boolean checkUserEmailExists(String email) {
        var user = userDetailsRepository.findUserEmail(email ) ;
        return user.isPresent();
    }
    public User addNewUser(User userDetails ) throws UsernameAlreadyExistsException, EmailAlreadyUsedException
    {
        if(checkUsernameExists(userDetails.getUsername())  ){
            throw new UsernameAlreadyExistsException();
        }else if(checkUserEmailExists(userDetails.getEmail())) {
            throw  new EmailAlreadyUsedException();
        }
        return userDetailsRepository.save(userDetails);
    }
}
