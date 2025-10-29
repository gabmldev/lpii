package com.github.gabmldev.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedNativeQueries({
        @NamedNativeQuery(name = "User.createUser", query = "INSERT INTO users (username, email, pwd) VALUES (:username :email, :pwd, :rid)"),
        @NamedNativeQuery(name = "User.restorePwd", query = "UPDATE users SET pwd = :pwd WHERE email = :email RETURNING email"),
        @NamedNativeQuery(name = "User.deleteUser", query = "DELETE FROM users WHERE username = :username AND email = :email RETURNING id, email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "pwd")
    private String pwd;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
