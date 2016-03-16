package com.example.app.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.example.app.entity.Role;
import com.example.app.entity.User;
import com.example.app.test.DAOTestBase;

public class UserDAOTest extends DAOTestBase {

    @Test
    public void find() {
        UserDAO userDAO = instance(UserDAO.class);

        User user = userDAO.findByUsername("john");
        assertEquals(Long.valueOf(1), user.getId());
        assertEquals(Role.USER, user.getRole());
    }

}
