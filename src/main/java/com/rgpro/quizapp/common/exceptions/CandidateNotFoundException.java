package com.rgpro.quizapp.common.exceptions;

public class CandidateNotFoundException extends Exception{
    public CandidateNotFoundException( Long candidateId,Long userId){
        super("%d tried to access candidate %d".formatted(userId,candidateId));
    }
}
