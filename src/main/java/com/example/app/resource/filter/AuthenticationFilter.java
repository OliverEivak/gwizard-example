package com.example.app.resource.filter;

import static com.example.app.guice.GuiceInjector.getInjector;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import com.example.app.dao.AuthenticationDAO;
import com.example.app.entity.Authentication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        String token = headers.getFirst("Auth-Token");
        String username = headers.getFirst("Username");

        log.info("AuthenticationFilter");
        Authentication authentication = getInjector().getInstance(AuthenticationDAO.class).findByToken(token);

        if (authentication != null && username.equals(authentication.getUser().getUsername())) {
            ApplicationPrincipal applicationPrincipal = new ApplicationPrincipal(authentication);
            ApplicationSecurityContext applicationSecurityContext = new ApplicationSecurityContext(applicationPrincipal);
            requestContext.setSecurityContext(applicationSecurityContext);
        }
    }
}
