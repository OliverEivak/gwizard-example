package com.example.app.dao;

import static org.gwizard.hibernate.EM.em;

import javax.persistence.TypedQuery;

import com.example.app.entity.User;

public class UserDAO extends BaseDAO<User> {

    public User findByUsername(String username) {
        TypedQuery<User> typedQuery = em().createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        typedQuery.setParameter("username", username);

        return getSingleResult(typedQuery);
    }

}
