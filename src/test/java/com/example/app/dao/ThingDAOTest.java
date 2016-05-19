package com.example.app.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.app.entity.Thing;
import com.example.app.test.DAOTestBase;

public class ThingDAOTest extends DAOTestBase {

    private ThingDAO thingDAO;

    @Before
    public void setup() {
        thingDAO = instance(ThingDAO.class);
    }

    @Test
    public void create() {
        Thing createdThing = thingDAO.create();
        assertNotNull(createdThing);
    }

    @Test
    public void find() {
        Thing createdThing = thingDAO.create();
        Thing foundThing = thingDAO.find(createdThing.getId());
        assertEquals(createdThing, foundThing);
    }

    @Test
    public void findPremadeThing() {
        Thing thing = thingDAO.find(1L);
        assertNotNull(thing);
        assertEquals("premade thing 1", thing.getName());
    }

    @Test
    public void findAll() {
        Thing createdThing = thingDAO.create();
        List<Thing> foundThings = thingDAO.findAll();

        assertTrue(foundThings.contains(createdThing));
    }

}
