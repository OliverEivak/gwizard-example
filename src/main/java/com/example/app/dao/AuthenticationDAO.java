package com.example.app.dao;

import static org.gwizard.hibernate.EM.em;

import javax.persistence.TypedQuery;

import com.example.app.entity.Authentication;

public class AuthenticationDAO extends BaseDAO<Authentication> {

    public Authentication findByToken(String token) {
        TypedQuery<Authentication> typedQuery = em().createQuery("SELECT s FROM Authentication s WHERE s.token = :token", Authentication.class);
        typedQuery.setParameter("token", token);

        return getSingleResult(typedQuery);
    }

}
