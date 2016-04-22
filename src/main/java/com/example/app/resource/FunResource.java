package com.example.app.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.example.app.config.ApplicationConfig;
import com.example.app.services.ExampleService;

import lombok.Data;

/**
 * This resource really isn't very fun.
 */
@Path("/fun")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FunResource extends BaseResource {

	@Inject
	private ApplicationConfig cfg;

	@Inject
	private HttpHeaders headers;

	@Inject
	private ExampleService exampleService;

	@Data
	public static class Stuff {
		private final String foo;
	}

	@GET
	public Stuff stuff() {
		return new Stuff(cfg.getFoo());
	}

	@Timed
	@GET
	@Path("/headers")
	public HttpHeaders headers() {
		return headers;
	}

	@Timed
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return exampleService.getHelloWorld();
	}
}
