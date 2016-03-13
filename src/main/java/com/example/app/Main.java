package com.example.app;

import static com.example.app.services.ShutdownService.SHUTDOWN;
import static com.example.app.services.ShutdownService.SHUTDOWN_SERVICE_PORT;

import java.io.File;
import java.net.InetAddress;

import org.gwizard.config.ConfigModule;
import org.gwizard.healthchecks.HealthChecksModule;
import org.gwizard.hibernate.HibernateModule;
import org.gwizard.logging.LoggingModule;
import org.gwizard.metrics.MetricsModule;
import org.gwizard.rest.RestModule;
import org.gwizard.services.Run;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.example.app.guice.GuiceInjector;
import com.example.app.resource.exception.ApplicationExceptionMapper;
import com.example.app.resource.filter.AuthenticationFilter;
import com.example.app.util.SocketUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Set up the injector and start all services
 */
public class Main {

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("First argument needs to be a yaml config file, doofus");
			return;
		}

		if (args.length == 2) {
			if ("stop".equals(args[1])) {
				SocketUtils.sendByte(InetAddress.getByName(null), SHUTDOWN_SERVICE_PORT, SHUTDOWN);
				System.exit(0);
			}
		}

		Injector injector = Guice.createInjector(
				new ApplicationModule(),
				new ConfigModule(new File(args[0]), ApplicationConfig.class),
				new LoggingModule(),
				new RestModule(),
				new HibernateModule(),
				new MetricsModule(),
				new HealthChecksModule());

		injector.getInstance(Run.class).start();
		GuiceInjector.setInjector(injector);

		ResteasyProviderFactory.getInstance().registerProvider(AuthenticationFilter.class);
		ResteasyProviderFactory.getInstance().registerProvider(ApplicationExceptionMapper.class);
	}

}
