package com.github.gabmldev.app.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.gabmldev.app.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findByUserIdAndJti(UUID userId, String jti);

    Session findByUserId(UUID userId);

    Optional<List<Session>> findAllByUserId(UUID userId);

    @Modifying
    @Query("DELETE FROM Session s WHERE s.user.id = :uid AND s.jti = :jti")
    void deleteSession(@Param("uid") UUID userId, @Param("jti") String jti);

    @Modifying
    @Query("DELETE FROM Session s WHERE s.user.id = :uid AND s.expiresAt < :now")
    void deleteExpiredSessions(
            @Param("uid") UUID userId,
            @Param("now") LocalDateTime now);
}
