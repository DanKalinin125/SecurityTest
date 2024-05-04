package com.example.SecurityTest.repository;

import com.example.SecurityTest.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
