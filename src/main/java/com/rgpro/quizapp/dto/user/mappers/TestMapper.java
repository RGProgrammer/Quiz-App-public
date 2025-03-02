package com.rgpro.quizapp.dto.user.mappers;

import com.rgpro.quizapp.dto.user.Test;

public final class TestMapper {
    private TestMapper() {}
    public static com.rgpro.quizapp.model.Test dtoToEntity(Test test){
        return new com.rgpro.quizapp.model.Test(
                test.getId(),
                null,null,
                test.getCreationDate(),
                test.getExpirationDate(),
                test.getTestToken(),
                null,null);
    }
    public static Test entityToDto(com.rgpro.quizapp.model.Test test){
        var quiz = QuizMapper.entityToDto(test.getQuiz()) ;
        var candidate = CandidateMapper.entityToDto(test.getTarget());
        var candidateAnswers = SubmissionMapper.entityToDto(test.getCandidateAnswers());
        return new Test(
                test.getId(),
                quiz,candidate,
                test.getCreationDate(),
                test.getExpirationDate(),
                test.getToken(),
                candidateAnswers);
    }
}
