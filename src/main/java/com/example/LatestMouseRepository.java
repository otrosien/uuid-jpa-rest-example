package com.example;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LatestMouseRepository extends JpaRepository<LatestMouse, UUID> {

}
