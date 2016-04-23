package com.example.app.test;

import org.gwizard.services.Run;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.junit.After;
import org.junit.Before;

import com.example.app.entity.Authentication;
import com.example.app.guice.module.RestModule;
import com.example.app.resource.ILoginResource;
import com.example.app.resource.ILogoutResource;
import com.example.app.resource.filter.AuthHeadersRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    }

    @After
    public void tearDownWebStack() throws Exception {
        injector.getInstance(Run.class).stop();
    }

    private ResteasyClient getClientWithObjectMapper() {
        ResteasyJackson2Provider provider = new ResteasyJackson2Provider();
        provider.setMapper(instance(ObjectMapper.class));

        return new ResteasyClientBuilder().register(provider).build();
    }

    protected T getClient(Class<T> resourceInterface) {
        ResteasyClient client = getClientWithObjectMapper();

        ResteasyWebTarget target = client.target(String.format("http://localhost:%s/", TEST_PORT));
        return target.proxy(resourceInterface);
    }

    protected T getClientWithAuthentication(Class<T> resourceInterface, String token, String username) {
        ResteasyClient client = getClientWithObjectMapper();
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
