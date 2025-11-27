package com.Mahmoud.AuthService.Repository;

import com.Mahmoud.AuthService.Model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AuthUser, Integer> {

    AuthUser findByUsername(String username);

    Integer deleteByUsername(String username);

    @Query("select a.username from AuthUser a where a.id = :userId")
    String findUsernameByUserId(Integer userId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
