package org.pidster.tomcat.embed.junit;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatApplicationBuilder;
import org.pidster.tomcat.embed.TomcatRuntime;

/**
 * @author swilliams
 *
 */
public class TomcatServerRule implements TestRule {

	private Class<? extends TomcatApplicationBuilderFactory> factoryClass;

	private TomcatServerConfig annotation;

	private TomcatRuntime runtime;

	private int port;

	private Set<ServletContainerInitializer> initializers = new HashSet<>();

	public TomcatServerRule() {
		this(SimpleTomcatApplicationBuilderFactory.class);
	}

	public TomcatServerRule(Class<? extends TomcatApplicationBuilderFactory> factoryClass) {
		this.factoryClass = factoryClass;
	}

	@Override
	public final Statement apply(Statement base, Description description) {
		this.annotation = description.getAnnotation(TomcatServerConfig.class);
		return statement(base, description);
	}

	private Statement statement(final Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				try {
					start();
					base.evaluate();
				}
				finally {
					stop();
				}
			}
		};
	}

	private void start() throws Exception {

		TomcatApplicationBuilderFactory builderFactory = factoryClass.newInstance();
		TomcatApplicationBuilder builder = builderFactory.getBuilder(annotation);

		System.out.println("before.addServletContainerInitializer()");

		for (ServletContainerInitializer initializer : initializers) {
			builder.addServletContainerInitializer(initializer);
			System.out.println("builder.addServletContainerInitializer(" + initializer + ")");
		}		

		System.out.println("after.addServletContainerInitializer()");

		this.port = annotation.port();

		Tomcat tomcat = builder.build();

		this.runtime = tomcat.start(annotation.timeout());
	}

	private void stop() {
		runtime.stop(annotation.timeout());
	}

	public final TomcatRuntime deploy(String appName) {
		return runtime.deploy(appName);
	}

	public final TomcatRuntime undeploy(String appName) {
		return runtime.undeploy(appName);
	}

	public final int getPort() {
		return port;
	}

}
