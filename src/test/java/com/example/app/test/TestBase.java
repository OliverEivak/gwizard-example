package com.example.app.test;

import org.gwizard.hibernate.HibernateModule;
import org.gwizard.logging.LoggingModule;
import org.junit.Before;

import com.example.app.ApplicationModule;
import com.example.app.guice.GuiceInjector;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import lombok.extern.slf4j.Slf4j;

/**
 * Some common behavior for all tests. Sets up an injector suitable for executing JAXRS resources
 * directly, exactly as the web container would.
 */
@Slf4j
public class TestBase {

	protected Injector injector;

	/** */
	@Before
	public void initializeTestBase() {
		injector = Guice.createInjector(
				Modules.override(
					new LoggingModule(),
					new HibernateModule(),
					new ApplicationModule(),
					new TestModule())
						.with(overrideModule()));

		GuiceInjector.setInjector(injector);
	}

	/** Override this method in a test class if you want special guice behavior */
	protected Module overrideModule() {
		return new EmptyModule();
	}

	/** Convenience method */
	protected <T> T instance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}
}
