package com.example.app.services;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.gwizard.services.Services;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.app.dao.ThingDAO;
import com.example.app.entity.Thing;

@RunWith(EasyMockRunner.class)
public class ThingServiceTest {

    @TestSubject
    private ThingService thingService = new ThingService(createNiceMock(Services.class));

    @Mock
    private ThingDAO thingDAO;

    @Test
    public void create() {
        Thing thing = createMock(Thing.class);
        expect(thingDAO.create()).andReturn(thing);

        replay(thingDAO);

        Thing returnedThing = thingService.create();

        verify(thingDAO);

        Assert.assertEquals(thing, returnedThing);
    }

}
