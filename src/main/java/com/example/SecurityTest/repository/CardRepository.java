package com.example.SecurityTest.repository;

import com.example.SecurityTest.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
