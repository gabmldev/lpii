package com.github.gabmldev.app.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries(
    {
        @NamedQuery(
            name = "Role.findAllNames",
            query = "SELECT r.name FROM role r",
            resultClass = String[].class
        ),
        @NamedQuery(
            name = "Role.findById",
            query = "SELECT r.name FROM role r WHERE r.id = :id",
            resultClass = String.class
        ),
    }
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Nullable
    private String description;

    @OneToMany(mappedBy = "role")
    private List<User> user;
}
