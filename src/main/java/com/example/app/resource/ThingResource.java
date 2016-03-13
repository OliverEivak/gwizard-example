package com.example.app.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.example.app.entity.Thing;
import com.example.app.services.ThingService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * At least this resource is named appropriately.
 */
@Path("/things")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ThingResource extends BaseResource {

    @Inject
    private ThingService thingService;

    @POST
    @Transactional
    public Thing create() {
        return thingService.create();
    }

    @Timed
    @GET
    @Transactional
    public List<Thing> list() {
        return thingService.findAll();
    }

    @Timed
    @GET
    @RolesAllowed({"USER"})
    @Transactional
    @Path("secret")
    public List<Thing> listSecretly() {
        return thingService.findAll();
    }

    @Timed
    @GET
    @Transactional
    @Path("ex")
    public Thing getException() {
        thingService.create(); // This will get rolled back
        throw new RuntimeException("Something happened");
    }

    @Timed
    @GET
    @Transactional
    @Path("{thingId}")
    public Thing get(@PathParam("thingId") Long thingId) {
        return thingService.find(thingId);
    }
}
