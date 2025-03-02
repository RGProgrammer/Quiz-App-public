package com.rgpro.quizapp.services;


import com.rgpro.quizapp.model.Question;
import com.rgpro.quizapp.repositories.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService {
    private QuestionRepository questionRepository;

    public List<Question> getAllOfQuestions(Long userId, Long quizId) {
       return questionRepository.findQuizQuestionsForUser(quizId,userId);
    }

    public Optional<Question> getQuestion(Long userId, Long quizId, Long questionId) {
        return questionRepository.findQuestion(questionId,quizId,userId);

    }


    public Long saveQuestion(Question question) {
        var res = questionRepository.save(question);
        return res.getId();
    }


    public List<Long> addQuestions(List<Question> questions) {
        var edited = new ArrayList<Long>();
        questions.forEach(question -> {
            var id = saveQuestion(question);
            if(id!= 0 ) edited.add(id);
        });
        return  edited ;
    }

    public Long deleteQuestion(Long questionID) {
        if(questionRepository.findById(questionID).isPresent()) {
            questionRepository.deleteById(questionID);
            return questionID ;
        }
        return 0L;
    }
}


