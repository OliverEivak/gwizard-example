package com.example.app.resource.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class AuthHeadersRequestFilter implements ClientRequestFilter {

    private final String token;
    private final String username;

    public AuthHeadersRequestFilter(String token, String username) {
        this.token = token;
        this.username = username;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        requestContext.getHeaders().add("Auth-Token", token);
        requestContext.getHeaders().add("Username", username);
    }
}