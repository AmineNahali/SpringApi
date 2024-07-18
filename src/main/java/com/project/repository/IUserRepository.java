package com.project.repository;

import com.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM app_user u WHERE app_user.email = :email")
    Optional<User> findByUserEmail(@Param("email") String email);

    @Query("SELECT u FROM app_user u WHERE app_user.username = :username")
    Optional<User> findByUserUsername(@Param("username") String username);
}