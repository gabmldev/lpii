package com.github.gabmldev.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
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
@NamedNativeQuery(name = "User.createUser", query = "INSERT INTO users (username, email, pwd) VALUES (:username :email, :pwd, :rid)")
@NamedNativeQuery(name = "User.restorePwd", query = "UPDATE users SET pwd = :pwd WHERE email = :email RETURNING email")
@NamedNativeQuery(name = "User.deleteUser", query = "DELETE FROM users WHERE username = :username AND email = :email RETURNING id, email")
@NamedNativeQuery(name = "User.findByName", query = "SELECT u FROM users u WHERE u.username = :username")
@NamedNativeQuery(name = "User.findByEmail", query = "SELECT u FROM users u WHERE u.email = :email")
@NamedNativeQuery(name = "User.findRole", query = "SELECT r.name FROM users u JOIN role r ON (u.role_id = r.id)")
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
