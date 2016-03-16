package com.example.app.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.example.app.entity.Thing;
import com.example.app.test.TestBase;

/**
 * This tests the resource methods against a real in-memory database, no need to mock and stub the whole data
 * layer away. Mocking the data layer is a horrible practice, since for CRUD apps 90% of your application _is_
 * interaction with the data layer. If you're going to screw something up, it's likely to be all the hibernate
 * interaction.
 */
public class ThingResourceTest extends TestBase {

	@Test
	public void thingsCanBeCreatedAndRetrieved() throws Exception {
		Thing created = instance(ThingResource.class).create();

		Thing fetched = instance(ThingResource.class).get(created.getId());

		assertEquals(created.getName(), fetched.getName());
	}

	@Test
	public void thingsCanBeListed() throws Exception {
		Thing created = instance(ThingResource.class).create();

		List<Thing> fetched = instance(ThingResource.class).list();

		assertTrue(fetched.size() > 1);
		assertTrue(fetched.contains(created));
	}

}
