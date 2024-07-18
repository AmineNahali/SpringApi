package com.project.repository;

import com.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    // Corrected JPQL query
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByUserEmail(@Param("email") String email);

    // Corrected JPQL query
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUserUsername(@Param("username") String username);

}