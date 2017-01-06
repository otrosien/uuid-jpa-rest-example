package com.example;

import javax.persistence.EntityManager;

import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MouseRepositoryEventListener extends AbstractRepositoryEventListener<Mouse> {

    private final EntityManager em;

    private final LatestMouseRepository repo;

    @Override
    protected void onBeforeCreate(Mouse entity) {
        generateNewId(entity);
        updateLatestMouseTo(entity);
    }

    @Override
    protected void onBeforeSave(Mouse entity) {
        em.detach(entity);
        generateNewId(entity);
        updateLatestMouseTo(entity);
    }

    @Override
    protected void onBeforeDelete(Mouse entity) {
        repo.deleteByMouse(entity);
    }

    private void updateLatestMouseTo(Mouse entity) {
        repo.save(new LatestMouse(entity.getName(), entity));
    }

    private void generateNewId(Mouse entity) {
        entity.setId(null);
    }
}
