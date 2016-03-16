package com.example.app.test;

import org.gwizard.rest.RestModule;
import org.gwizard.services.Run;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.After;
import org.junit.Before;

import com.example.app.entity.Authentication;
import com.example.app.resource.Login;
import com.example.app.resource.exception.ApplicationExceptionMapper;
import com.example.app.resource.filter.AuthHeadersRequestFilter;
import com.example.app.resource.filter.AuthenticationFilter;
import com.google.inject.Module;

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
        return new RestModule();
    }

    @Before
    public void setUpWebStack() throws Exception {
        injector.getInstance(Run.class).start();

        registerResteasyProviders();
    }

    @After
    public void tearDownWebStack() throws Exception {
        injector.getInstance(Run.class).stop();
    }

    private void registerResteasyProviders() {
        ResteasyProviderFactory.getInstance().registerProvider(AuthenticationFilter.class);
        ResteasyProviderFactory.getInstance().registerProvider(ApplicationExceptionMapper.class);
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
        Login loginClient = target.proxy(Login.class);

        Login.LoginForm loginForm = new Login.LoginForm(username, password);
        return loginClient.login(loginForm);
    }

    protected void logout() {
        // TODO
    }

}
