package com.github.gabmldev.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.gabmldev.app.entity.DeletedUser;
import com.github.gabmldev.app.entity.User;

@Repository
public interface AuthRepository extends JpaRepository<User, String> {
        @Query(value = "User.createUser", nativeQuery = true)
        public User createUser(
                        @Param("username") String usrn,
                        @Param("email") String email,
                        @Param("pwd") String password,
                        @Param("rid") String roleId);

        @Query(value = "User.restorePwd", nativeQuery = true)
        public String restoreUserPwd(
                        @Param("pwd") String password,
                        @Param("email") String email);

        @Query(value = "User.deleteUser", nativeQuery = true)
        public DeletedUser deleteUser(
                        @Param("username") String usrn,
                        @Param("email") String email);

        @Query(value = "User.findByName", nativeQuery = true)
        public User findByName(@Param("username") String username);

        @Query(value = "User.findByEmail", nativeQuery = true)
        public User findByEmail(@Param("email") String email);

        @Query(value = "User.findRole", nativeQuery = true)
        public String findRole();
}
