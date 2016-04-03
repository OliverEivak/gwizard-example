package com.example.app.guice.module;

import com.example.app.resource.filter.ApplicationPersistFilter;
import com.google.inject.servlet.ServletModule;

public class ApplicationRestModule extends ServletModule {

    @Override
    protected void configureServlets() {
        filter("/*").through(ApplicationPersistFilter.class);
    }

}
