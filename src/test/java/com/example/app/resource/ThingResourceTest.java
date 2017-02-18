package com.example.app.resource;

import static org.gwizard.hibernate.EM.em;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityTransaction;

import com.example.app.entity.Thing;
import com.example.app.test.TestBase;

import org.junit.Test;

/**
 * This tests the resource methods against a real in-memory database, no need to mock and stub the whole data
 * layer away. Mocking the data layer is a horrible practice, since for CRUD apps 90% of your application _is_
 * interaction with the data layer. If you're going to screw something up, it's likely to be all the hibernate
 * interaction.
 */
public class ThingResourceTest extends TestBase {

	@Test
	public void thingsCanBeCreatedAndRetrieved() throws Exception {
        startTransaction();
		Thing created = instance(ThingResource.class).create();
        closeTransaction();

        startTransaction();
		Thing fetched = instance(ThingResource.class).get(created.getId());
        closeTransaction();

		assertEquals(created.getName(), fetched.getName());
	}

	@Test
	public void thingsCanBeListed() throws Exception {
        startTransaction();
		Thing created = instance(ThingResource.class).create();
		closeTransaction();

        startTransaction();
		List<Thing> fetched = instance(ThingResource.class).list();
        closeTransaction();

		assertTrue(fetched.size() > 1);
		assertTrue(fetched.contains(created));
	}

	// Separate methods for transactions because this test does not start the full web stack including the persist filter

	private void startTransaction() {
        em().getTransaction().begin();
    }

    private void closeTransaction() {
        EntityTransaction transaction = em().getTransaction();
        if (transaction.isActive()) {
            if (transaction.getRollbackOnly()) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }
}


