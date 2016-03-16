package com.example.app.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mindrot.jbcrypt.BCrypt;

import com.example.app.entity.Authentication;
import com.example.app.entity.User;
import com.example.app.services.LoginService;
import com.example.app.services.UserService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationResource extends BaseResource implements Registration {

    @Inject
    private UserService userService;

    @Inject
    private LoginService loginService;

    @POST
    @Transactional
    public Authentication register(RegistrationForm registrationForm) {
        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setPassword(BCrypt.hashpw(registrationForm.getPassword(), BCrypt.gensalt()).getBytes());
        user = userService.create(user);
        log.info(String.format("User %s has registered", user.getUsername()));

        return loginService.login(registrationForm.getUsername(), registrationForm.getPassword());
    }

}
