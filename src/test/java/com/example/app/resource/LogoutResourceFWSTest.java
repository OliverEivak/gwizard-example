package com.example.app.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.ws.rs.ClientErrorException;

import org.junit.Test;

import com.example.app.entity.Authentication;
import com.example.app.test.FullWebStackTestBase;

public class LogoutResourceFWSTest extends FullWebStackTestBase<Logout> {

    @Test
    public void testLogout() throws Exception {
        Authentication authentication = login("john", "test");

        Logout client = getClientWithAuthentication(Logout.class, //
                authentication.getToken(), authentication.getUser().getUsername());

        client.logout();
    }

    @Test
    public void testLogoutWhenNotLoggedIn() throws Exception {
        Logout client = getClient(Logout.class);

        try {
            client.logout();
            fail("Exception expected");
        } catch (ClientErrorException e) {
            assertEquals("HTTP 403 Forbidden", e.getMessage());
        }
    }

}
