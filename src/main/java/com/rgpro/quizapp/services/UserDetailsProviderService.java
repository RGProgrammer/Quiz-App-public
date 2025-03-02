package com.rgpro.quizapp.services;

import com.rgpro.quizapp.model.User;
import com.rgpro.quizapp.repositories.UserDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsProviderService implements UserDetailsService {
    private UserDetailsRepository userDetailsRepository ;
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userDetailsRepository.findUsername(username ) ;
        if(user.isEmpty()){
            throw new UsernameNotFoundException("");
        }
        return user.get() ;
//        User user = null ;
//        if(user== null){
//            throw new UsernameNotFoundException("");
//        }
//        return user ;
    }
    public  User loadUserByUserId(Long userId)
    {
        var user = userDetailsRepository.findById(userId) ;
        if(user.isEmpty()){
            throw new UsernameNotFoundException("");
        }
        return user.get() ;
    }
}
