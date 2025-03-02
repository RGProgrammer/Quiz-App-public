package com.rgpro.quizapp.repositories;


import com.rgpro.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Modifying
    @Transactional
    @Query("UPDATE questions q " +
            "SET q.text = :#{#question.text}, q.resource = :#{#question.resource}, q.quiz_Id = :#{#question.quiz_Id} " +
            "WHERE q.id = :#{#question.id} AND  q.quiz_Id = :#{#question.quiz_Id}")
    Question update(@Param("question") Question question);

    @Query("SELECT q FROM questions q " +
            "JOIN quizzes z ON q.quiz_Id = z.id " +
            "JOIN z.owner u " +
            "WHERE q.id = :id AND z.id = :quiz AND u.id = :user")
    Optional<Question> findQuestion(@Param("id") Long questionId,@Param("quiz") Long quizId, @Param("user")Long userId);

    @Query("SELECT q FROM questions q " +
            "JOIN quizzes z ON q.quiz_Id = z.id " +
            "JOIN z.owner u " +
            "WHERE z.id = :quizId AND u.id = :userId")
    List<Question> findQuizQuestionsForUser(@Param("quizId") Long quizId, @Param("userId") Long userId);
}
