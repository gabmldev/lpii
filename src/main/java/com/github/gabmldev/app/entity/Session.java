package com.github.gabmldev.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
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
@NamedQueries(
    {
        @NamedQuery(
            name = "Session.findSession",
            query = "SELECT * FROM session WHERE user_id = :uid AND tjti = :jti LIMIT 1",
            resultClass = Session.class
        ),
        @NamedQuery(
            name = "Session.findByUserId",
            query = "SELECT s FROM session s WHERE user_id = :uid LIMIT 1",
            resultClass = Session.class
        ),
        @NamedQuery(
            name = "Session.findAllSessions",
            query = "SELECT * FROM session WHERE user_id = :uid",
            resultClass = Session[].class
        ),
        @NamedQuery(
            name = "Session.createSession",
            query = "INSERT INTO session (tjti, token, user_id, created_at, expires_at) VALUES (:jti, :t, :uid, :created_at, :expires_at)",
            resultClass = Session.class
        ),
        @NamedQuery(
            name = "Session.updateSession",
            query = "UPDATE session SET tjti = :jti, token = :t, expires_at = :expires_at WHERE user_id = :uid AND tjti = :jti"
        ),
        @NamedQuery(
            name = "Session.deleteSession",
            query = "DELETE FROM session WHERE user_id = :uid AND tjti = :jti"
        ),
        @NamedQuery(
            name = "Session.deleteExpiredSessions",
            query = "DELETE FROM session WHERE user_id = :uid AND expires_at < NOW()"
        ),
    }
)
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
