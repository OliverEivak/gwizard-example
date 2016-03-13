package com.example.app.services;

import java.util.List;

import javax.inject.Inject;

import org.gwizard.services.Services;

import com.example.app.dao.ThingDAO;
import com.example.app.entity.Thing;
import com.google.common.util.concurrent.AbstractIdleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThingService extends AbstractIdleService {

    @Inject
    private ThingDAO thingDAO;

    @Inject
    public ThingService(Services services) {
        services.add(this);
    }

    @Override
    protected void startUp() throws Exception {
        log.debug("ThingService starting...");
    }

    @Override
    protected void shutDown() throws Exception {
        log.debug("ThingService shutting down...");
    }

    public Thing create() {
        return thingDAO.create();
    }

    public Thing find(Long id) {
        return thingDAO.find(id);
    }

    public List<Thing> findAll() {
        return thingDAO.findAll();
    }
}
