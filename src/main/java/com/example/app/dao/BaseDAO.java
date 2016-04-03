package com.example.app.dao;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class BaseDAO<Entity> {

    @Inject
    private Provider<EntityManager> entityManagerProvider;

    protected EntityManager em() {
        return entityManagerProvider.get();
    }

    public Entity update(Entity entity) {
        EntityManager em = em();

        Entity merged = em.merge(entity);
        em.persist(merged);
        return merged;
    }

    public void remove(Entity entity) {
        EntityManager em = em();

        Entity merged = em.merge(entity);
        em.remove(merged);
    }

    protected Entity getSingleResult(TypedQuery<Entity> typedQuery) {
        Entity entity = null;

        try {
            entity = typedQuery.getSingleResult();
        } catch (NoResultException ignored) {

        }

        return entity;
    }

}
