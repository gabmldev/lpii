package com.github.gabmldev.app.repository;

import com.github.gabmldev.app.entity.Session;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
    Optional<Session> findByUserIdAndJti(String userId, String jti);

    Session findByUserId(String userId);

    Optional<List<Session>> findAllByUserId(String userId);

    @Modifying
    @Query("DELETE FROM Session s WHERE s.user_id = :uid AND s.jti = :jti")
    void deleteSession(@Param("uid") String userId, @Param("jti") String jti);

    @Modifying
    @Query(
        "DELETE FROM Session s WHERE s.user_id = :uid AND s.expiresAt < :now"
    )
    void deleteExpiredSessions(
        @Param("uid") String userId,
        @Param("now") LocalDateTime now
    );
}
