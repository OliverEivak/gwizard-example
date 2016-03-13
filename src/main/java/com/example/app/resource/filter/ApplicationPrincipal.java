package com.example.app.resource.filter;

import java.security.Principal;

import com.example.app.entity.Authentication;
import com.example.app.entity.Role;

public class ApplicationPrincipal implements Principal {

    private Authentication authentication;

    public ApplicationPrincipal (Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public String getName() {
        return String.format("%s:%s", authentication.getToken(), authentication.getUser().getUsername());
    }

    public Role getRole() {
        return authentication.getUser().getRole();
    }
}
