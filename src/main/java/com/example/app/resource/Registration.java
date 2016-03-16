package com.example.app.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.app.entity.Authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface Registration {

    @POST
    Authentication register(RegistrationForm registrationForm);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class RegistrationForm {
        private String username;
        private String password;
    }

}
