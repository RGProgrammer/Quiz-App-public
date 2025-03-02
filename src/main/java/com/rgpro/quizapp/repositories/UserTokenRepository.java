package com.rgpro.quizapp.repositories;

import com.rgpro.quizapp.model.TokenRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserTokenRepository extends JpaRepository<TokenRecord,Long> {

    @Query(value = "SELECT tr from tokens tr where tr.token =:token")
    Optional<TokenRecord> findRecordByToken(@Param("token")String token);

    @Modifying
    @Query("delete from tokens tr where tr.user.id =:id and tr.token=:token")
    Integer deleteByUserId(@Param("id")Long id,@Param("token")String token);

    @Modifying
    @Query("delete from tokens tr where tr.user.id =:id")
    void deleteAllByUserId(@Param("id")Long id);
}
