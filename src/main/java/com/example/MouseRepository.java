package com.example;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MouseRepository extends JpaRepository<Mouse, UUID> {

    Optional<Mouse> findByName(String name);

    Mouse findById(UUID id);

}
