package com.example.app.resource.filter;

import java.io.IOException;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

import lombok.extern.slf4j.Slf4j;

/**
 * A modified {@link com.google.inject.persist.PersistFilter} that catches any IllegalStateException thrown when
 * persistService.start() is called more than once.
 *
 * The point of using UnitOfWork here is to get a new EntityManager for every request.
 */

@Slf4j
@Singleton
public final class ApplicationPersistFilter implements Filter {

    private final UnitOfWork unitOfWork;
    private final PersistService persistService;
    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public ApplicationPersistFilter(UnitOfWork unitOfWork, PersistService persistService,
            Provider<EntityManager> entityManagerProvider) {
        this.unitOfWork = unitOfWork;
        this.persistService = persistService;
        this.entityManagerProvider = entityManagerProvider;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            persistService.start();
        } catch (IllegalStateException ignored) {
            // The service was already started.
            // Calling start() multiple times should be ok according to the docs, but it's not :(
        }
    }

    public void destroy() {
        persistService.stop();
    }

    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException {

        unitOfWork.begin();
        beginTransaction();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            setRollbackOnly();
            throw e; // propagate exception
        } finally {
            try {
                closeTransaction();
            } catch (IllegalStateException | PersistenceException e) {
                log.error("Failed to close transaction", e);
            }
            unitOfWork.end();
        }
    }

    private void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    private void setRollbackOnly() {
        getEntityManager().getTransaction().setRollbackOnly();
    }

    private void closeTransaction() {
        EntityTransaction transaction = getEntityManager().getTransaction();
        if (transaction.isActive()) {
            if (transaction.getRollbackOnly()) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerProvider.get();
    }
}

