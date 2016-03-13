package com.example.app;

import org.gwizard.healthchecks.HealthChecksConfig;
import org.gwizard.hibernate.DatabaseConfig;
import org.gwizard.logging.LoggingConfig;
import org.gwizard.web.WebConfig;

import com.example.app.web.ApplicationWebConfig;

import lombok.Data;

/**
 * Your configuration class can be any POJO, and there's nothing special about
 * the property names - although we recommend sticking to these standards.
 * Your Guice module will need to @Provide the relevant config classes for
 * logging, web, database, etc, and this is where they should come from.
 */
@Data
public class ApplicationConfig {

	/** A bit of configuration for your own app */
	private String foo;

	/** Some standard bits of configuration */
	private LoggingConfig logging = new LoggingConfig();
	private ApplicationWebConfig web = new ApplicationWebConfig();
	private DatabaseConfig database = new DatabaseConfig();
	private HealthChecksConfig healthChecks = new HealthChecksConfig();
}
