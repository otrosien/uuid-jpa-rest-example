package com.example;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(exported=false)
public interface LatestMouseRepository extends JpaRepository<LatestMouse, UUID> {

    @Modifying
    @Transactional
    void deleteByMouse(Mouse mouse);
}
