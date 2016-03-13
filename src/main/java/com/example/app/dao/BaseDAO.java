package com.example.app.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class BaseDAO<Entity> {

    protected Entity getSingleResult(TypedQuery<Entity> typedQuery) {
        Entity entity = null;

        try {
            entity = typedQuery.getSingleResult();
        } catch (NoResultException ignored) {

        }

        return entity;
    }

}
