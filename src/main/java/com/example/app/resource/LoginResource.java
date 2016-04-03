package com.example.app.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.app.entity.Authentication;
import com.example.app.services.LoginService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource extends BaseResource implements ILoginResource {

    @Inject
    private LoginService loginService;

    @POST
    @Transactional
    public Authentication login(ILoginResource.LoginForm loginForm) {
        return loginService.login(loginForm.getUsername(), loginForm.getPassword());
    }

}
