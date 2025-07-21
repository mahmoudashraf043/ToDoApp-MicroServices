package com.Mahmoud.AuthService.Repository;

import com.Mahmoud.AuthService.Model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthUser, Integer> {
    AuthUser findByUsername(String username);

    void deleteByUsername(String username);
}
