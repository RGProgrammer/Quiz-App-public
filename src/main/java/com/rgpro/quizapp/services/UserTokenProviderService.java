package com.rgpro.quizapp.services;


import com.rgpro.quizapp.model.TokenRecord;
import com.rgpro.quizapp.model.User;
import com.rgpro.quizapp.repositories.UserTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserTokenProviderService {
    private UserTokenRepository userTokenRepository ;

    public Long isValidToken(String token)
    {
        var record = userTokenRepository.findRecordByToken(token);
        if(record.isPresent()){
            return record.get().getUser().getId();
        }
        return 0L ;
    }
    public Long isUserValidToken(User user, String token)
    {
        var record = userTokenRepository.findRecordByToken(token);
        if(record.isPresent() && record.get().getUser().getId() == user.getId()){
            return record.get().getUser().getId();
        }
        return 0L ;
    }

    public boolean isExpiredToken(Long id)
    {
        var tokenRecord = userTokenRepository.findById(id);
        return !tokenRecord.isPresent()||
                tokenRecord.get().getExpirationDate().isBefore(LocalDateTime.now())||
                tokenRecord.get().isExpired() ;
    }

    public Long removeTokenForUser(User user,String token )
    {
        //TODO set All tokens expired
        var record = userTokenRepository.findRecordByToken(token);
        if(record.isPresent() && Objects.equals(record.get().getUser().getId(), user.getId())){
            var actualToken = record.get() ;
            actualToken.setExpired(true);
            return userTokenRepository.save(actualToken).getId();
        }
        //userTokenRepository.deleteByUserId(user.getId(),token);
        return 0L ;
    }

    public void removeAllTokensForUser(User user)
    {
        //TODO set All tokens expired
        //userTokenRepository.deleteAllByUserId(user.getId());
    }

    public Long addUserToken(User user, String token,
                             LocalDateTime creationDate,
                             LocalDateTime expirationDate)
    {
        TokenRecord rToken = new TokenRecord(user, token,creationDate,expirationDate);
        TokenRecord res = userTokenRepository.save(rToken);
        return res.getUser().getId();
    }
}
