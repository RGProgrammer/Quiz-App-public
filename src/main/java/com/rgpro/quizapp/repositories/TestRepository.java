package com.rgpro.quizapp.repositories;

import com.rgpro.quizapp.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TestRepository extends JpaRepository<Test,Long> {

    @Query(value = "Select t from tests t where t.token =:token")
    Optional<Test> findTestByToken(@Param("token") String token);

    @Query(value = "Select t from tests t " +
            "where t.owner.id =:userId")
    List<Test> findTestsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT t FROM tests t " +
            "where t.owner.id =:userId AND t.id = :id")
    Optional<Test> findById( @Param("id")Long id , @Param("userId")Long userId);
}
