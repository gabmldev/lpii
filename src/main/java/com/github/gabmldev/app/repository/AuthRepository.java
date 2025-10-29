package com.github.gabmldev.app.repository;

import com.github.gabmldev.app.entity.DeletedUser;
import com.github.gabmldev.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthRepository extends JpaRepository<User, String> {
    @Query(value = "User.createUser", nativeQuery = true)
    public User createUser(@Param("username") String usr_n, @Param("email") String email, @Param("pwd") String password, @Param("rid") String roleId);
    @Query(value = "User.restorePwd", nativeQuery = true)
    public String restoreUserPwd(@Param("pwd") String password, @Param("email") String email);
    @Query(value = "User.deleteUser", nativeQuery = true)
    public DeletedUser deleteUser(@Param("username") String usr_n, @Param("email") String email);
}
