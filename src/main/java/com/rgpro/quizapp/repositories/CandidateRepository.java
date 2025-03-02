package com.rgpro.quizapp.repositories;

import com.rgpro.quizapp.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CandidateRepository extends JpaRepository<Candidate,Long> {
    //@Query(value ="select c from candidates c where c.owner =:userId")
    @Query("SELECT c FROM candidates c " +
            "WHERE  c.owner.id = :userId")
    List<Candidate> findCandidatesByUserId(@Param("userId") Long userId);
    //@Query(value ="select c from candidates c where c.owner =:userId and c.id =:id")
    @Query("SELECT c FROM candidates c " +
            "WHERE  c.owner.id = :userId and c.id=:id")
    Optional<Candidate> findById(@Param("id") Long candidateId,@Param("userId") Long userId);
}
