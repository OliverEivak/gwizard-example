package com.example.app.test;

import org.gwizard.rest.RestModule;
import org.gwizard.services.Run;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.After;
import org.junit.Before;

import com.example.app.entity.Authentication;
import com.example.app.guice.module.ApplicationRestModule;
import com.example.app.resource.ILoginResource;
import com.example.app.resource.ILogoutResource;
import com.example.app.resource.filter.AuthHeadersRequestFilter;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>This starts the full web stack and allows to issue real http requests against the target. </p>
 */
@Slf4j
public class FullWebStackTestBase<T> extends TestBase {

    public static int TEST_PORT = 18080;

    /**
     * We need to get the RestModule included if we want the web stack to run
     */
    @Override
    protected Module overrideModule() {
        return Modules.combine(new RestModule(), new ApplicationRestModule());
    }

    @Before
    public void setUpWebStack() throws Exception {
        injector.getInstance(Run.class).start();
    }

    @After
    public void tearDownWebStack() throws Exception {
        injector.getInstance(Run.class).stop();
    }

    protected T getClient(Class<T> resourceInterface) {
        ResteasyClient client = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = client.target(String.format("http://localhost:%s/", TEST_PORT));
        return target.proxy(resourceInterface);
    }

    protected T getClientWithAuthentication(Class<T> resourceInterface, String token, String username) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(new AuthHeadersRequestFilter(token, username));

        ResteasyWebTarget target = client.target(String.format("http://localhost:%s/", TEST_PORT));
        return target.proxy(resourceInterface);
    }

    protected Authentication login(String username, String password) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(String.format("http://localhost:%s/", TEST_PORT));
        ILoginResource loginClient = target.proxy(ILoginResource.class);

        ILoginResource.LoginForm loginForm = new ILoginResource.LoginForm(username, password);
        return loginClient.login(loginForm);
    }

    protected void logout(Authentication authentication) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(new AuthHeadersRequestFilter(authentication.getToken(), authentication.getUser().getUsername()));

        ResteasyWebTarget target = client.target(String.format("http://localhost:%s/", TEST_PORT));
        ILogoutResource logoutClient = target.proxy(ILogoutResource.class);

        logoutClient.logout();
    }

}
