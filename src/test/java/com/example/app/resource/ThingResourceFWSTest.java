package com.example.app.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.junit.Test;

import com.example.app.entity.Thing;
import com.example.app.test.FullWebStackTestBase;

public class ThingResourceFWSTest extends FullWebStackTestBase<ThingResourceFWSTest.ThingsClient> {

	@Path("/things")
	public static interface ThingsClient {
		@POST
		Thing create();

		@GET
		List<Thing> list();

		@GET
		@Path("{thingId}")
		Thing get(@PathParam("thingId") Long thingId);

		@GET
		@RolesAllowed({"USER"})
		@Path("secret")
		List<Thing> listSecretly();

		@GET
		@Path("ex")
		Thing getException();
	}

	@Test
	public void thingsCanBeCreatedAndRetrieved() throws Exception {
		ThingsClient things = getClient(ThingsClient.class);

		Thing created = things.create();

		Thing fetched = things.get(created.getId());

		assertEquals(created.getName(), fetched.getName());
	}

	@Test
	public void thingsCanBeListed() throws Exception {
		ThingsClient things = getClient(ThingsClient.class);

		Thing created = things.create();

		List<Thing> fetched = things.list();

		assertTrue(fetched.size() > 1);
		assertTrue(fetched.contains(created));
	}

	@Test
	public void secretThingsCanNotBeListedWithoutAuthentication() throws Exception {
		ThingsClient things = getClient(ThingsClient.class);

		try {
			things.listSecretly();
			fail("Exception expected");
		} catch (ClientErrorException expected) {

		}
	}

	@Test
	public void secretThingsCanBeListedWithAuthentication() throws Exception {
		ThingsClient things = getClientWithAuthentication(ThingsClient.class, "asd", "john");

		Thing created = things.create();

		List<Thing> fetched = things.list();

		assertTrue(fetched.size() > 1);
		assertTrue(fetched.contains(created));
	}

}
