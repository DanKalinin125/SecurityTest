package com.example.SecurityTest.repository;

import com.example.SecurityTest.entity.MyRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRoleRepository extends JpaRepository<MyRole, Long> {
    MyRole findByName(String roleUser);
}
