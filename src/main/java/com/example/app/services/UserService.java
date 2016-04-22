package com.example.app.services;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.example.app.dao.UserDAO;
import com.example.app.entity.Role;
import com.example.app.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class UserService {

    @Inject
    private UserDAO userDAO;

    public User create(User user) {
        user.setRole(Role.USER);
        return userDAO.update(user);
    }
}
