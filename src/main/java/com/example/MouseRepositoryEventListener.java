package com.example;

import java.lang.reflect.Field;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MouseRepositoryEventListener extends AbstractRepositoryEventListener<Mouse> {

    private final EntityManager em;

    private final MouseRepository repo;

    @Override
    protected void onBeforeCreate(Mouse entity) {
        Field id = ReflectionUtils.findField(Mouse.class, "id");
        ReflectionUtils.makeAccessible(id);
        UUID randomUUID = UUID.randomUUID();
        ReflectionUtils.setField(id, entity, randomUUID);
    }

    @Override
    protected void onBeforeSave(Mouse entity) {
        em.detach(entity);
        if(entity.getId() != null) {
            Mouse origMouse = repo.findOne(entity.getId());
            origMouse.setLatest(null);
            repo.save(origMouse);
        }
        onBeforeCreate(entity);
    }

}
