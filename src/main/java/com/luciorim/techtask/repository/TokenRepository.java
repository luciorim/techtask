package com.luciorim.techtask.repository;

import com.luciorim.techtask.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
        select t from Token t
        join User u on u.id = t.user.id
        where t.revoked = false and t.expired = false
    """)
    List<Token> findAllValidTokenByUser(Long userId);

    Optional<Token> findByToken(String token);

}
