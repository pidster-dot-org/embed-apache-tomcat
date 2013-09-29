/*
 * Copyright 2013 pidster
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * @author pidster
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

		for (ServletContainerInitializer initializer : initializers) {
			builder.addServletContainerInitializer(initializer);
		}		

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
