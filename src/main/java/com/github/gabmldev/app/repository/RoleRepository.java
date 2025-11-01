package com.github.gabmldev.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.gabmldev.app.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("SELECT r.name FROM Role r")
    List<String> findAllNames();

    @Query("SELECT r.name FROM Role r WHERE r.id = :id")
    String findNameById(UUID id);
}
