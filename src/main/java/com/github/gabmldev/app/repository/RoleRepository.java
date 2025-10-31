package com.github.gabmldev.app.repository;

import com.github.gabmldev.app.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("SELECT r.name FROM Role r")
    List<String> findAllNames();

    @Query("SELECT r.name FROM Role r WHERE r.id = :id")
    String findNameById(String id);
}
