package com.example.app.services;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.inject.Inject;

import org.gwizard.services.Services;
import org.mindrot.jbcrypt.BCrypt;

import com.example.app.dao.AuthenticationDAO;
import com.example.app.dao.UserDAO;
import com.example.app.entity.Authentication;
import com.example.app.entity.User;
import com.google.common.util.concurrent.AbstractIdleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginService extends AbstractIdleService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private AuthenticationDAO authenticationDAO;

    private SecureRandom random = new SecureRandom();

    @Inject
    public LoginService(Services services) {
        services.add(this);
    }

    @Override
    protected void startUp() throws Exception {
        log.debug("LoginService starting...");
    }

    @Override
    protected void shutDown() throws Exception {
        log.debug("LoginService shutting down...");
    }

    public Authentication login(String username, String password) {
        User user = userDAO.findByUsername(username);

        if (user != null && BCrypt.checkpw(password, new String(user.getPassword()))) {
            log.info(String.format("User %s logged in using password", username));
            return getAuthentication(user);
        }

        log.info(String.format("User %s failed to log in", username));
        return null;
    }

    private Authentication getAuthentication(User user) {
        Authentication authentication = new Authentication();
        authentication.setUser(user);
        authentication.setToken(new BigInteger(130, random).toString(32));

        return authenticationDAO.update(authentication);
    }
}
