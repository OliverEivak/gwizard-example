package com.example.app.services;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.gwizard.services.Services;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.app.dao.AuthenticationDAO;
import com.example.app.entity.Authentication;
import com.example.app.entity.User;

@RunWith(EasyMockRunner.class)
public class LogoutServiceTest {

    @TestSubject
    private LogoutService logoutService = new LogoutService(createNiceMock(Services.class));

    @Mock
    private AuthenticationDAO authenticationDAO;

    @Test
    public void logout() throws Exception {
        Authentication authentication = new Authentication();
        User user = new User();
        user.setUsername("user-who-is-logging-out");
        authentication.setUser(user);

        authenticationDAO.remove(authentication);

        replay(authenticationDAO);

        logoutService.logout(authentication);

        verify(authenticationDAO);
    }

}
