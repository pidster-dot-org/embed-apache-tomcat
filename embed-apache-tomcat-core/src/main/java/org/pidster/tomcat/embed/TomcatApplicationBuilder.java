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
package org.pidster.tomcat.embed;

import java.net.URL;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContextListener;
import javax.servlet.descriptor.JspConfigDescriptor;

import org.apache.catalina.Container;
import org.apache.catalina.Loader;
import org.apache.catalina.Manager;
import org.apache.catalina.WebResourceRoot;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.JarScanner;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;

/**
 * @author pidster
 *
 */
public interface TomcatApplicationBuilder extends TomcatContainerBuilder<TomcatHostBuilder, TomcatApplicationBuilder>, Collector<TomcatApplicationBuilder, Container> {

	/**
	 * @return this
	 */
    TomcatApplicationBuilder withDefaultConfig();

	/**
	 * @return this
	 */
    TomcatApplicationBuilder makeDirs();

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setContextAttribute(String attribute, Object value);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setContextInitParameter(String initParameter, String value);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletContainerInitializer(Class<? extends ServletContainerInitializer> listenerClass);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletContainerInitializer(Class<? extends ServletContainerInitializer> listenerClass, Set<Class<?>> classes);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletContainerInitializer(ServletContainerInitializer sci);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletContainerInitializer(ServletContainerInitializer sci, Set<Class<?>> classes);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletContextListener(Class<? extends ServletContextListener> listenerClass);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletContextListener(Class<? extends ServletContextListener> listenerClass, Map<String, String> config);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletContextListener(ServletContextListener listener);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletFilter(Class<? extends Filter> filterClass, String... mappings);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletFilter(Class<? extends Filter> filterClass, Map<String, String> config, String... mappings);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletFilter(Filter filter, String... patterns);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletFilter(Filter filter, Map<String, String> initParameters, String... urlPatterns);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServletFilter(Filter filter, Map<String, String> initParameters, EnumSet<DispatcherType> dispatcherTypes, String... urlPatterns);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, String... mappings);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, Map<String, String> config, String... mappings);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServlet(String name, Class<? extends Servlet> servletClass, String... mappings);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServlet(String name, Class<? extends Servlet> servletClass, Map<String, String> config, String... mappings);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServlet(Servlet servlet, Map<String, String> config, String... patterns);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addServlet(Servlet servlet, String name, Map<String, String> config, String... patterns);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addApplicationParameter(ApplicationParameter parameter);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addConstraint(SecurityConstraint constraint);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addErrorPage(ErrorPage errorPage);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addLocaleEncodingMappingParameter(String locale, String encoding);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addMimeMapping(String extension, String mimeType);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addParameter(String name, String value);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addRoleMapping(String role, String link);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addSecurityRole(String role);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addWatchedResource(String name);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder addWelcomeFile(String name);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setAllowCasualMultipartParsing(boolean allowCasualMultipartParsing);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setSessionManager(Manager sessionManager);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setConfigFile(URL configFile);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setCookies(boolean cookies);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setUseHttpOnly(boolean useHttpOnly);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setSessionCookieName(String sessionCookieName);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setSessionCookieDomain(String sessionCookieDomain);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setSessionCookiePath(String sessionCookiePath);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setSessionCookiePathUsesTrailingSlash(boolean sessionCookiePathUsesTrailingSlash);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setCrossContext(boolean crossContext);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setDistributable(boolean distributable);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setLoginConfig(LoginConfig config);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setReloadable(boolean reloadable);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setPrivileged(boolean privileged);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setSessionTimeout(int timeout);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setSwallowAbortedUploads(boolean swallowAbortedUploads);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setSwallowOutput(boolean swallowOutput);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setXmlValidation(boolean xmlValidation);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setXmlNamespaceAware(boolean xmlNamespaceAware);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setResourceOnlyServlets(String resourceOnlyServlets);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setEffectiveMajorVersion(int major);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setEffectiveMinorVersion(int minor);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setWebappVersion(String webappVersion);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setFireRequestListenersOnForwards(boolean enable);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setPreemptiveAuthentication(boolean enable);

	/**
	 * @return this
	 */
    TomcatApplicationBuilder setSendRedirectBody(boolean enable);

	/**
	 * @return this
	 */
	TomcatApplicationBuilder setDenyUncoveredHttpMethods(boolean denyUncoveredHttpMethods);

	/**
	 * @return this
	 */
	TomcatApplicationBuilder setDocBase(String docBase);

	/**
	 * @return this
	 */
	TomcatApplicationBuilder setIgnoreAnnotations(boolean ignoreAnnotations);

	/**
	 * @return this
	 */
	TomcatApplicationBuilder setJarScanner(JarScanner jarScanner);

	/**
	 * @return this
	 */
	TomcatApplicationBuilder setLogEffectiveWebXml(boolean logEffectiveWebXml);

	/**
	 * @return this
	 */
	TomcatApplicationBuilder setInstanceManager(InstanceManager instanceManager);

	/**
	 * @return this
	 */
	TomcatApplicationBuilder setJspConfigDescriptor(JspConfigDescriptor descriptor);

	/**
	 * @return this
	 */
	TomcatApplicationBuilder setLoader(Loader loader);

	/**
	 * @return this
	 */
	TomcatApplicationBuilder setResources(WebResourceRoot resources);

}