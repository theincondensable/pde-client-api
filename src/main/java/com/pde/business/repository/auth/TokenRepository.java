package com.pde.business.repository.auth;

import com.pde.business.model.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author abbas
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = "select t from Token t where t.user.id = :userId")
    Optional<Token> findByUserId(Long userId);

}
