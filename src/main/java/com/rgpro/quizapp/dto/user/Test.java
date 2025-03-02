package com.rgpro.quizapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Test {
    private Long id ;
    private Quiz quiz ;
    private Candidate candidate ;
    private LocalDateTime creationDate ;
    private LocalDateTime expirationDate ;
    private String testToken ;
    private Submission candidateAnswers ;

}
