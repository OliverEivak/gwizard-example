package com.example.app.guice.module;

import com.example.app.config.ApplicationConfig;
import com.example.app.resource.filter.ApplicationPersistFilter;
import com.example.app.config.ApplicationWebConfig;
import com.google.inject.Provides;

public class RestModule extends org.gwizard.rest.RestModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();
        filter("/*").through(ApplicationPersistFilter.class);
    }

    @Provides
    public ApplicationWebConfig applicationWebConfig(ApplicationConfig cfg) {
        return cfg.getWeb();
    }

}
