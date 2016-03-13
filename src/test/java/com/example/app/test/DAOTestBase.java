package com.example.app.test;

import static org.gwizard.hibernate.EM.em;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;

public class DAOTestBase extends JunitTestBase {

    @Before
    public void beginTransaction() {
        em().getTransaction().begin();
    }

    @After
    public void closeTransaction() {
        EntityTransaction transaction = em().getTransaction();
        if (transaction.isActive()) {
            if (transaction.getRollbackOnly()) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

}
