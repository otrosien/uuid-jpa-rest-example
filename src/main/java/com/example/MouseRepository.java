package com.example;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MouseRepository extends JpaRepository<Mouse, UUID> {

}
