package org.pidster.tomcat.embed.junit;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class TestServletContainerInitializer implements
		ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx)
			throws ServletException {
		// TODO Auto-generated method stub
	}

}
