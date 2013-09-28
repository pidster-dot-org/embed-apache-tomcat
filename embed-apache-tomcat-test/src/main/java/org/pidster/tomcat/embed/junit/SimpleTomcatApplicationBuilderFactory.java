package org.pidster.tomcat.embed.junit;

import java.io.File;

import javax.servlet.ServletContainerInitializer;

import org.pidster.tomcat.embed.TomcatApplicationBuilder;
import org.pidster.tomcat.embed.TomcatFactory;

public class SimpleTomcatApplicationBuilderFactory implements
		TomcatApplicationBuilderFactory {

	@Override
	public TomcatApplicationBuilder getBuilder(TomcatServerConfig annotation) {

		String baseDir = annotation.baseDir();
		if ("".equals(baseDir)) {
			baseDir = System.getProperty("user.dir");
		}

		File baseFile = new File(baseDir);
		String appName = annotation.appName();
		if ("".equals(appName)) {
			appName = "test";
		}

		File appDir = new File(baseDir, String.format("webapps/%s", appName));
		if (!appDir.exists()) {
			appDir.mkdirs();
		}

		TomcatApplicationBuilder builder = new TomcatFactory().create()
				.newMinimalServer(baseFile, annotation.port())
					.createApplication(annotation.appName())
						.setStartStopThreads(1)
						.withDefaultConfig();

		for (Class<? extends ServletContainerInitializer> initializer : annotation.value()) {
			builder.addServletContainerInitializer(initializer);
			System.out.println("annotation.addServletContainerInitializer(" + initializer + ")");
		}

		return builder;
	}

}
