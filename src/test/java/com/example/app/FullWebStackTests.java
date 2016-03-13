package com.example.app;

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

import org.gwizard.rest.RestModule;
import org.gwizard.services.Run;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.app.entity.Thing;
import com.example.app.resource.filter.AuthHeadersRequestFilter;
import com.example.app.test.JunitTestBase;
import com.google.inject.Module;

/**
 * <p>This test starts the full web stack and issues real http requests against the target. Compare this against
 * the ThingsResourceTests which provide a more direct test of resource classes. Starting the full HTTP stack
 * does give you a more "thorough" test, but at the expense of more work and slower tests.</p>
 *
 * <p>IMNSHO, directly testing resources provides a "sweet spot" that lets you build a large amount of coverage
 * very quickly. However, you may wish to throw in a few web stack tests anyways.</p>
 */
public class FullWebStackTests extends JunitTestBase {

	/** We need to get the RestModule included if we want the web stack to run */
	@Override
	protected Module overrideModule() {
		return new RestModule();
	}

	@Before
	public void setUpWebStack() throws Exception {
		injector.getInstance(Run.class).start();
	}

	@After
	public void tearDownWebStack() throws Exception {
		injector.getInstance(Run.class).stop();
	}

	/**
	 * This is for the RESTeasy getClient framework. If you want to do a lot of this this kind of coding/testing,
	 * it's a good idea to put the interface in the main project and make the resource classes impl the interface.
	 */
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

	private ThingsClient getClient() {
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:18080/");
		return target.proxy(ThingsClient.class);
	}

	private ThingsClient getClientWithAuthentication(String token, String username) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new AuthHeadersRequestFilter(token, username));

		ResteasyWebTarget target = client.target("http://localhost:18080/");
		return target.proxy(ThingsClient.class);
	}

	@Test
	public void thingsCanBeCreatedAndRetrieved() throws Exception {
		ThingsClient things = getClient();

		Thing created = things.create();

		Thing fetched = things.get(created.getId());

		assertEquals(created.getName(), fetched.getName());
	}

	@Test
	public void thingsCanBeListed() throws Exception {
		ThingsClient things = getClient();

		Thing created = things.create();

		List<Thing> fetched = things.list();

		assertTrue(fetched.size() > 1);
		assertTrue(fetched.contains(created));
	}

	@Test
	public void secretThingsCanNotBeListedWithoutAuthentication() throws Exception {
		ThingsClient things = getClient();

		try {
			List<Thing> fetched = things.listSecretly();
			fail("Exception expected");
		} catch (ClientErrorException expected) {

		}
	}

	@Test
	public void secretThingsCanBeListedWithAuthentication() throws Exception {
		ThingsClient things = getClientWithAuthentication("asd", "john");

		Thing created = things.create();

		List<Thing> fetched = things.list();

		assertTrue(fetched.size() > 1);
		assertTrue(fetched.contains(created));
	}

}
