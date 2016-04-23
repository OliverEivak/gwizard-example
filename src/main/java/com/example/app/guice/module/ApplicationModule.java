package com.example.app.guice.module;

import org.gwizard.hibernate.DatabaseConfig;
import org.gwizard.logging.LoggingConfig;
import org.gwizard.web.WebServer;

import com.example.app.config.ApplicationConfig;
import com.example.app.guice.provider.ObjectMapperProvider;
import com.example.app.resource.BaseResource;
import com.example.app.util.ReflectionUtils;
import com.example.app.web.ApplicationWebServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.Service;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

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
		bindResources();
		bindServices();

		bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class);

		bind(WebServer.class).to(ApplicationWebServer.class);
	}

	private void bindServices() {
		for (Class service : ReflectionUtils.getClasses("com.example.app.services", Service.class)) {
			log.debug("Binding service " + service.getSimpleName());
			bind(service).asEagerSingleton();
		}
	}

	private void bindResources() {
		for (Class resource : ReflectionUtils.getClasses("com.example.app.resource", BaseResource.class)) {
			log.debug("Binding resource " + resource.getSimpleName());
			bind(resource);
		}
	}

	@Provides
	public LoggingConfig loggingConfig(ApplicationConfig cfg) {
		return cfg.getLogging();
	}

	@Provides
	public DatabaseConfig databaseConfig(ApplicationConfig cfg) {
		return cfg.getDatabase();
	}
}
