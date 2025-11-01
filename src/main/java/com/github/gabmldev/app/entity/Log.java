package com.github.gabmldev.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "trigger")
    private String trigger;
    
    @Column(name = "origin")
    private String origin;

    @Column(name = "payload")
    private String payload;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
