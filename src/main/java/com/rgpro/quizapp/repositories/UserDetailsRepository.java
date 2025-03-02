package com.rgpro.quizapp.repositories;

import com.rgpro.quizapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserDetailsRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT u from users u where u.username =:username")
    Optional<User> findUsername(@Param("username")String username );

    @Query(value="SELECT u from users u where u.email =:email")
    Optional<User> findUserEmail(@Param("email")String email);
}
