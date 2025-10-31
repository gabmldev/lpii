package com.github.gabmldev.app.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.gabmldev.app.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
    @Query(name = "Session.findSession", nativeQuery = true)
    Optional<Session> findSession(
            @Param("uid") String userId,
            @Param("jti") String tjti);

    @Query(name = "Session.findAllSessions", nativeQuery = true)
    Optional<List<Session>> findAllSessions(@Param("uid") String userId);

    @Query(name = "Session.deleteExpiredSessions", nativeQuery = true)
    void deleteExpiredSession(
            @Param("uid") String userId,
            @Param("expires_at") LocalDateTime eat);

    @Query(name = "Session.updateSession", nativeQuery = true)
    void updateSession(
            @Param("uid") String userId,
            @Param("jti") String tji,
            @Param("t") String token,
            @Param("expires_at") LocalDateTime eat);

    @Query(name = "Session.createSession", nativeQuery = true)
    void saveSession(
            @Param("id") String id,
            @Param("jti") String jti,
            @Param("t") String token,
            @Param("uid") String userId,
            @Param("created_at") LocalDateTime cat,
            @Param("expires_at") LocalDateTime eat);
}
