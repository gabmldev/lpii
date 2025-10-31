package com.github.gabmldev.app.repository;

import com.github.gabmldev.app.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query(
        value = "SELECT r.name FROM Users u JOIN Role r ON (u.role_id = r.id)",
        nativeQuery = true
    )
    public String findRole();
}
