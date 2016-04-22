package com.example.app.services;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mindrot.jbcrypt.BCrypt;

import com.example.app.dao.AuthenticationDAO;
import com.example.app.dao.UserDAO;
import com.example.app.entity.Authentication;
import com.example.app.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class LoginService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private AuthenticationDAO authenticationDAO;

    private SecureRandom random = new SecureRandom();

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
