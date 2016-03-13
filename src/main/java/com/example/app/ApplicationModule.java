package com.example.app;

import javax.inject.Singleton;

import org.gwizard.hibernate.DatabaseConfig;
import org.gwizard.logging.LoggingConfig;
import org.gwizard.web.WebConfig;
import org.gwizard.web.WebServer;

import com.example.app.services.ExampleService;
import com.example.app.services.ShutdownService;
import com.example.app.services.ThingService;
import com.example.app.util.ReflectionUtils;
import com.example.app.web.ApplicationWebConfig;
import com.example.app.web.ApplicationWebServer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import io.dropwizard.jackson.Jackson;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Among the duties of your application module(s), you must explicitly bind every JAXRS resource class.
 * Consider using Reflections to do this automatically.</p>
 *
 * <p>We must provide bindings for the LoggingConfig, WebConfig, and DatabaseConfig to use the
 * logging, rest, and hibernate modules.</p>
 */
@Slf4j
public class ApplicationModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(WebServer.class).to(ApplicationWebServer.class);

		for (Class resource : ReflectionUtils.getResourceClasses()) {
			log.debug("Binding resource " + resource.getSimpleName());
			bind(resource);
		}

		bind(ShutdownService.class).asEagerSingleton();
		bind(ExampleService.class).asEagerSingleton();
		bind(ThingService.class).asEagerSingleton();
	}

	/** This objectmapper will get used for RESTEasy's JSON responses */
	@Provides
	@Singleton
	public ObjectMapper objectMapper() {
		return Jackson.newObjectMapper() //
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) //
				.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Provides
	public LoggingConfig loggingConfig(ApplicationConfig cfg) {
		return cfg.getLogging();
	}

	@Provides
	public ApplicationWebConfig applicationWebConfig(ApplicationConfig cfg) {
		return cfg.getWeb();
	}

	@Provides
	public DatabaseConfig databaseConfig(ApplicationConfig cfg) {
		return cfg.getDatabase();
	}
}
