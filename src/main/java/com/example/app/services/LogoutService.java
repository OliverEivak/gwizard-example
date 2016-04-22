package com.example.app.services;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.example.app.dao.AuthenticationDAO;
import com.example.app.entity.Authentication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class LogoutService  {

    @Inject private AuthenticationDAO authenticationDAO;

    public void logout(Authentication authentication) {
        log.info(String.format("User %s is logging out", authentication.getUser().getUsername()));
        authenticationDAO.remove(authentication);
    }

}
