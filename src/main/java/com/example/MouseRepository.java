package com.example;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MouseRepository extends PagingAndSortingRepository<Mouse, UUID> {

    @Query("SELECT m FROM Mouse m JOIN LatestMouse lm ON lm.id=m.id WHERE m.name=:name")
    Optional<Mouse> findLatestMouseByName(String name);

    @Override
    @Query("SELECT m FROM Mouse m JOIN LatestMouse lm ON lm.id=m.id")
    Page<Mouse> findAll(Pageable pageable);

    Mouse findById(UUID id);
}
