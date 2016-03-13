package com.example.app.dao;

import static org.gwizard.hibernate.EM.em;

import java.util.List;

import com.example.app.entity.Thing;

public class ThingDAO {

    private static int count = 0;

    public Thing create() {
        final Thing thing = new Thing("thing " + count++);
        em().persist(thing);
        return thing;
    }

    public Thing find(Long thingId) {
        return em().find(Thing.class, thingId);
    }

    public List<Thing> findAll() {
        return em().createQuery("SELECT t FROM Thing t", Thing.class).getResultList();
    }

}
