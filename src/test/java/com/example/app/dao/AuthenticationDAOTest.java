package com.example.app.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.example.app.entity.Authentication;
import com.example.app.test.DAOTestBase;

public class AuthenticationDAOTest extends DAOTestBase {

    @Test
    public void find() {
        AuthenticationDAO authenticationDAO = instance(AuthenticationDAO.class);

        Authentication foundAuthentication = authenticationDAO.findByToken("qwe");
        assertEquals("jane", foundAuthentication.getUser().getUsername());
    }

}
