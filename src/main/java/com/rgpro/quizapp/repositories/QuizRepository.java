package com.rgpro.quizapp.repositories;

import com.rgpro.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface QuizRepository extends JpaRepository<Quiz,Long> {

    @Query(value = "SELECT q from quizzes q " +
            " where q.id =:id and q.owner.id = :userId")
    Optional<Quiz> findQuizById(@Param("id")Long id,@Param("userId") Long UserId);
    @Query(value = "SELECT q from quizzes q where q.owner.id =:id")
    List<Quiz> findQuizzesByOwnerId(@Param("id")Long id);
}
