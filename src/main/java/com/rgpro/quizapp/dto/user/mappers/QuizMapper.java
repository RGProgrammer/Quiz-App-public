package com.rgpro.quizapp.dto.user.mappers;

import com.rgpro.quizapp.dto.user.Quiz;

import javax.swing.text.html.parser.Entity;

public final class QuizMapper {
    private QuizMapper() {}

    public static Quiz entityToDto(com.rgpro.quizapp.model.Quiz quiz)
    {
        return new Quiz(quiz.getId(),
                quiz.getTitle(),
                quiz.getContent()
        );
    }

    public static com.rgpro.quizapp.model.Quiz dtoToEntity(Quiz quiz)
    {
       return new com.rgpro.quizapp.model.Quiz(
                quiz.getTitle(),
                quiz.getContent(),
                null,
                null);
    }
}
