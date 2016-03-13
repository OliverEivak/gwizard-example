package com.example.app.services;

import static org.easymock.EasyMock.createNiceMock;

import org.easymock.EasyMockRunner;
import org.easymock.TestSubject;
import org.gwizard.services.Services;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class ExampleServiceTest {

    @TestSubject
    private ExampleService exampleService = new ExampleService(createNiceMock(Services.class));

    @Test
    public void getHelloWorld() throws Exception {
        Assert.assertEquals("Hello World", exampleService.getHelloWorld());
    }

}
