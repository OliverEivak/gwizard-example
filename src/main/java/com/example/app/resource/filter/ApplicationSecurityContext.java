package com.example.app.resource.filter;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class ApplicationSecurityContext implements SecurityContext {

    private ApplicationPrincipal principal;

    public ApplicationSecurityContext(ApplicationPrincipal principal) {
        this.principal = principal;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return role.equals(principal.getRole().toString());
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
