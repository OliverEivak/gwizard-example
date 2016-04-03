package com.example.app.resource.filter;

import java.io.IOException;

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

/**
 * A modified {@link com.google.inject.persist.PersistFilter} that catches
 * any IllegalStateException thrown when persistService.start() is called
 * more than once.
 */

@Singleton
public final class ApplicationPersistFilter implements Filter {
    private final UnitOfWork unitOfWork;
    private final PersistService persistService;

    @Inject
    public ApplicationPersistFilter(UnitOfWork unitOfWork, PersistService persistService) {
        this.unitOfWork = unitOfWork;
        this.persistService = persistService;
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
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            unitOfWork.end();
        }
    }
}
