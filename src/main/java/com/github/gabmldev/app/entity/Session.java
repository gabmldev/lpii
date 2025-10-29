package com.github.gabmldev.app.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedNativeQueries({
        @NamedNativeQuery(name = "Session.findSession", query = "SELECT * FROM session WHERE user_id = :uid AND tjti = :jti LIMIT 1"),
        @NamedNativeQuery(name = "Session.findAllSessions", query = "SELECT * FROM session WHERE user_id = :uid"),
        @NamedNativeQuery(name = "Session.createSession", query = "INSERT INTO session (id, tjti, token, user_id, created_at, expires_at) VALUES (:id, :jti, :t, :uid, :created_at, :expires_at)"),
        @NamedNativeQuery(name = "Session.updateSession", query = "UPDATE session SET tjti = :jti, token = :t, expires_at = :expires_at WHERE user_id = :uid AND tjti = :jti"),
        @NamedNativeQuery(name = "Session.deleteExpiredSessions", query = "DELETE FROM session WHERE user_id = :uid AND expires_at < NOW()")
})
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "tjti", nullable = false)
    private String jti;

    @Column(name = "token", nullable = false, columnDefinition = "TEXT")
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
