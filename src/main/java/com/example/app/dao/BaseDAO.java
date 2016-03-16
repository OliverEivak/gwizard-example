package com.example.app.dao;

import static org.gwizard.hibernate.EM.em;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class BaseDAO<Entity> {

    public Entity update(Entity entity) {
        Entity merged = em().merge(entity);
        em().persist(merged);
        return merged;
    }

    public void remove(Entity entity) {
        Entity merged = em().merge(entity);
        em().remove(merged);
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
