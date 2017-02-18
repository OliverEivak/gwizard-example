package com.example.app.web;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.gwizard.web.EventListenerScanner;
import org.gwizard.web.HandlerScanner;
import org.gwizard.web.WebServer;

import com.example.app.config.ApplicationWebConfig;

@Singleton
public class ApplicationWebServer extends WebServer {

    private final ApplicationWebConfig applicationWebConfig;

    @Inject
    public ApplicationWebServer(ApplicationWebConfig applicationWebConfig, EventListenerScanner eventListenerScanner,
            HandlerScanner handlerScanner) {
        super(applicationWebConfig, eventListenerScanner, handlerScanner);
        this.applicationWebConfig = applicationWebConfig;
    }

    @Override
    protected ServletContextHandler createRootServletContextHandler() {
        ServletContextHandler sch = new ServletContextHandler(null, "/");
        applicationWebConfig.getInitParameters().forEach(sch::setInitParameter);
        return sch;
    }

}
