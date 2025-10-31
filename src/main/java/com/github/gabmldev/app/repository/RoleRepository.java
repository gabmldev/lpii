package com.github.gabmldev.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.gabmldev.app.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(name = "Role.findAllNames", nativeQuery = true)
    public String[] findAllNames();
    @Query(name = "Role.findById", nativeQuery = true)
    public String findRoleNameById(@Param("id") String id);
}
