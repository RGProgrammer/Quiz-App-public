package com.rgpro.quizapp.services;

import com.rgpro.quizapp.common.exceptions.CandidateNotFoundException;
import com.rgpro.quizapp.common.exceptions.QuizNotFoundException;
import com.rgpro.quizapp.common.exceptions.TestNotFoundException;
import com.rgpro.quizapp.dto.user.Test;
import com.rgpro.quizapp.dto.user.mappers.TestMapper;
import com.rgpro.quizapp.model.User;
import com.rgpro.quizapp.repositories.QuizRepository;
import com.rgpro.quizapp.repositories.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TestService {
    private final TestRepository testRepository ;
    private final CandidateService candidateService ;
    private final QuizRepository quizRepository ;

    public List<Test> getTestListFromUserId(Long userId) {
        var tests = testRepository.findTestsByUserId(userId);
        return tests.stream()
                .map(TestMapper::entityToDto)
                .toList() ;

    }

    public Test getTestById(Long id, Long userId) throws TestNotFoundException {
        var optTest = testRepository.findById(id , userId) ;
        if(optTest.isEmpty()) {
            throw new TestNotFoundException();
        }else{
            return TestMapper.entityToDto(optTest.get());
        }
    }

    public Long createNewTest(Test dto, User user) throws CandidateNotFoundException {
        var test = TestMapper.dtoToEntity(dto);
        var quiz = quizRepository.findQuizById(dto.getQuiz().getId(), user.getId());
        test.setQuiz(quiz.get());
        test.setOwner(user);
        test.setTarget(candidateService.getCandidate(dto.getCandidate().getId(), user.getId()));
        return testRepository.save(test).getId() ;
    }

    public Long deleteTest(Long id, Long userId)  throws TestNotFoundException{
        var test = this.getTestById(id,userId);
        testRepository.deleteById(test.getId());
        return test.getId();
    }
}
