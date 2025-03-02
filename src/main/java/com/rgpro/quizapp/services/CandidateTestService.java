package com.rgpro.quizapp.services;

import com.rgpro.quizapp.common.exceptions.TestNotFoundException;
import com.rgpro.quizapp.common.exceptions.TestSubmissionExpiredException;
import com.rgpro.quizapp.dto.candidate.*;
import com.rgpro.quizapp.model.CandidateChoice;
import com.rgpro.quizapp.repositories.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CandidateTestService {
    private final TestRepository testRepository;

    public Test getTestByToken(String token)
            throws TestNotFoundException {
        var optTest = testRepository.findTestByToken(token);
        if (optTest.isPresent()) {
            var test = optTest.get();
            var quiz = new Quiz(test.getQuiz().getId(),
                    test.getQuiz().getTitle(),
                    test.getQuiz().getContent(),
                    test.getQuiz().getQuestions()
                            .stream().map(
                                    question -> {
                                        var choices = question.getChoices().stream().map(
                                                choice -> new Choice(choice.getId(),
                                                        choice.getText())).toList();
                                        return new Question(question.getId(),
                                                question.getText(), choices);
                                    }).toList());
            return new Test(test.getId(),
                    quiz,
                    token,
                    test.getCreationDate(),
                    test.getExpirationDate());
        } else {
            throw new TestNotFoundException();
        }
    }

    public Long submit(Submission candidateSubmission)
            throws TestSubmissionExpiredException , TestNotFoundException{
        //TODO covert dto to model
        var optTest = testRepository.findTestByToken(candidateSubmission.getTestToken());
        if(optTest.isPresent()){
            var test = optTest.get();
            if(isSubmissionExpired(test)){
                throw new TestSubmissionExpiredException();
            }else {
                test.setCandidateAnswers(
                        new com.rgpro.quizapp.model.Submission(
                                0L,
                                candidateSubmission.getChoices()
                                        .stream().map(choice ->
                                     new CandidateChoice(
                                             choice.getQuestionId(),
                                             choice.getChoiceId(),
                                             choice.isAnswer())
                                ).toList(), LocalDateTime.now()
                        ));
                return testRepository.save(test)
                        .getId();
            }
        }else{
            throw new TestNotFoundException();
        }
    }

    private boolean isSubmissionExpired(com.rgpro.quizapp.model.Test test){
        return test.getExpirationDate().isBefore(LocalDateTime.now()) || test.getCandidateAnswers() != null;
    }
}
