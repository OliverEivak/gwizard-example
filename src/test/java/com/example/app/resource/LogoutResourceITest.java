package com.example.app.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.ws.rs.ClientErrorException;

import org.junit.Test;

import com.example.app.entity.Authentication;
import com.example.app.test.IntegrationTestBase;

public class LogoutResourceITest extends IntegrationTestBase<ILogoutResource> {

    @Test
    public void testLogout() throws Exception {
        Authentication authentication = login("john", "test");

        ILogoutResource client = getClientWithAuthentication(authentication);

        client.logout();
    }

    @Test
    public void testLogoutWhenNotLoggedIn() throws Exception {
        ILogoutResource client = getClient();

        try {
            client.logout();
            fail("Exception expected");
        } catch (ClientErrorException e) {
            assertEquals("HTTP 403 Forbidden", e.getMessage());
        }
    }

}
