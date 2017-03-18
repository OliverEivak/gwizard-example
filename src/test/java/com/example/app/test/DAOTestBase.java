package com.example.app.test;

import static org.gwizard.hibernate.EM.em;

import javax.persistence.EntityTransaction;

import com.example.app.guice.GuiceInjector;
import com.example.app.guice.module.ApplicationModule;
import com.example.app.guice.module.TestModule;
import com.google.inject.Guice;
import com.google.inject.util.Modules;

import org.gwizard.hibernate.HibernateModule;
import org.gwizard.logging.LoggingModule;
import org.junit.After;
import org.junit.Before;

public class DAOTestBase extends TestBase {

    @Before
    @Override
    public void initializeTestBase() {
        injector = Guice.createInjector(
                Modules.override(
                        new LoggingModule(),
                        new HibernateModule(),
                        new ApplicationModule() {
                            @Override
                            protected void configure() {
                                // not needed for DAO tests
                            }
                        },
                        new TestModule())
                        .with(overrideModule()));

        GuiceInjector.setInjector(injector);

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
