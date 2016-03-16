package com.example.app.services;

import javax.inject.Inject;

import org.gwizard.services.Services;

import com.example.app.dao.UserDAO;
import com.example.app.entity.Role;
import com.example.app.entity.User;
import com.google.common.util.concurrent.AbstractIdleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService extends AbstractIdleService {

    @Inject
    private UserDAO userDAO;

    @Inject
    public UserService(Services services) {
        services.add(this);
    }

    @Override
    protected void startUp() throws Exception {
        log.debug("UserService starting...");
    }

    @Override
    protected void shutDown() throws Exception {
        log.debug("UserService shutting down...");
    }

    public User create(User user) {
        user.setRole(Role.USER);
        return userDAO.update(user);
    }
}
