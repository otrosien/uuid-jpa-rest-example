package com.example;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MouseRepository extends JpaRepository<Mouse, UUID> {

    @Query("SELECT m FROM Mouse m JOIN LatestMouse lm ON m.id=lm.mouse.id WHERE lm.name=:name")
    Optional<Mouse> findLatestMouseByName(String name);

    @Query("SELECT m FROM Mouse m LEFT JOIN LatestMouse lm ON m.id=lm.mouse.id WHERE m.name=:name AND lm.mouse.id IS NULL")
    Page<Mouse> findOldMiceByName(String name, Pageable pageable);

    Optional<Mouse> findOneById(UUID id);

    @Override
    @Query("SELECT m FROM Mouse m JOIN LatestMouse lm ON lm.mouse.id=m.id")
    Page<Mouse> findAll(Pageable pageable);

}
