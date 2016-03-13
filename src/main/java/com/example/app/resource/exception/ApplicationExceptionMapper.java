package com.example.app.resource.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
@Singleton
public class ApplicationExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        log.error("Exception", exception);

        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("500: Internal Server error")
                .type("text/plain").build();
    }
}
