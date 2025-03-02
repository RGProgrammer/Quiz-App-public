package com.rgpro.quizapp.services;

import com.rgpro.quizapp.common.exceptions.QuizNotFoundException;
import com.rgpro.quizapp.dto.user.Quiz;
import com.rgpro.quizapp.dto.user.mappers.QuizMapper;
import com.rgpro.quizapp.model.User;
import com.rgpro.quizapp.repositories.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes(Long user)
    {
        var res = quizRepository.findQuizzesByOwnerId(user);
        return res.stream().map(QuizMapper::entityToDto).toList() ;
    }

    public Quiz getQuiz(Long quizId, Long userId) throws QuizNotFoundException {
        return quizRepository.findQuizById(quizId , userId)
                .map(QuizMapper::entityToDto)
                .orElseThrow(QuizNotFoundException::new);
    }


    public Long saveQuiz(Quiz dto, User user)
    {
        var quiz = QuizMapper.dtoToEntity(dto);
        quiz.setOwner(user);
        var res = quizRepository.save(quiz);
        return res.getId();
    }

    public Long deleteQuiz(Long User, Long quizID)
    {
        quizRepository.deleteById(quizID);
        return quizID ;
    }
}
