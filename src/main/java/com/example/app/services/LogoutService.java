package com.example.app.services;

import javax.inject.Inject;

import org.gwizard.services.Services;

import com.example.app.dao.AuthenticationDAO;
import com.example.app.entity.Authentication;
import com.google.common.util.concurrent.AbstractIdleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogoutService extends AbstractIdleService {

    @Inject private AuthenticationDAO authenticationDAO;

    @Inject
    public LogoutService(Services services) {
        services.add(this);
    }

    @Override
    protected void startUp() throws Exception {
        log.debug("LogoutService starting...");
    }

    @Override
    protected void shutDown() throws Exception {
        log.debug("LogoutService shutting down...");
    }

    public void logout(Authentication authentication) {
        log.info(String.format("User %s is logging out", authentication.getUser().getUsername()));
        authenticationDAO.remove(authentication);
    }

}
