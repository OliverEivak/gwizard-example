package com.example.app.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.example.app.entity.Thing;
import com.example.app.test.DAOTestBase;

public class ThingDAOTest extends DAOTestBase {

    @Test
    public void create() {
        ThingDAO thingDAO = instance(ThingDAO.class);

        Thing createdThing = thingDAO.create();
        assertNotNull(createdThing);
    }

    @Test
    public void find() {
        ThingDAO thingDAO = instance(ThingDAO.class);

        Thing createdThing = thingDAO.create();
        Thing foundThing = thingDAO.find(createdThing.getId());
        assertEquals(createdThing, foundThing);
    }

    @Test
    public void findPremadeThing() {
        ThingDAO thingDAO = instance(ThingDAO.class);

        Thing thing = thingDAO.find(1L);
        assertNotNull(thing);
        assertEquals("premade thing 1", thing.getName());
    }

    @Test
    public void findAll() {
        ThingDAO thingDAO = instance(ThingDAO.class);

        Thing createdThing = thingDAO.create();
        List<Thing> foundThings = thingDAO.findAll();

        assertTrue(foundThings.contains(createdThing));
    }

}
