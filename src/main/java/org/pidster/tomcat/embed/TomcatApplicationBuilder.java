/*
   Copyright 2013 pid[at]pidster.org

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
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

import org.apache.catalina.Container;
import org.apache.catalina.Manager;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;


public interface TomcatApplicationBuilder extends TomcatContainerBuilder<TomcatHostBuilder, TomcatApplicationBuilder>, Collector<TomcatApplicationBuilder, Container> {

    TomcatApplicationBuilder withDefaultConfig();

    TomcatApplicationBuilder makeDirs();

    TomcatApplicationBuilder setContextAttribute(String attribute, Object value);

    TomcatApplicationBuilder setContextInitParameter(String initParameter, String value);

    TomcatApplicationBuilder addServletContainerInitializer(Class<? extends ServletContainerInitializer> listenerClass);

    TomcatApplicationBuilder addServletContainerInitializer(Class<? extends ServletContainerInitializer> listenerClass, Set<Class<?>> classes);

    TomcatApplicationBuilder addServletContainerInitializer(ServletContainerInitializer sci);

    TomcatApplicationBuilder addServletContainerInitializer(ServletContainerInitializer sci, Set<Class<?>> classes);

    TomcatApplicationBuilder addServletContextListener(Class<? extends ServletContextListener> listenerClass);

    TomcatApplicationBuilder addServletContextListener(Class<? extends ServletContextListener> listenerClass, Map<String, String> config);

    TomcatApplicationBuilder addServletContextListener(ServletContextListener listener);

    TomcatApplicationBuilder addServletFilter(Class<? extends Filter> filterClass, String... mappings);

    TomcatApplicationBuilder addServletFilter(Class<? extends Filter> filterClass, Map<String, String> config, String... mappings);

    TomcatApplicationBuilder addServletFilter(Filter filter, String... patterns);

    TomcatApplicationBuilder addServletFilter(Filter filter, Map<String, String> initParameters, String... urlPatterns);

    TomcatApplicationBuilder addServletFilter(Filter filter, Map<String, String> initParameters, EnumSet<DispatcherType> dispatcherTypes, String... urlPatterns);

    TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, String... mappings);

    TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, Map<String, String> config, String... mappings);

    TomcatApplicationBuilder addServlet(String name, Class<? extends Servlet> servletClass, String... mappings);

    TomcatApplicationBuilder addServlet(String name, Class<? extends Servlet> servletClass, Map<String, String> config, String... mappings);

    TomcatApplicationBuilder addServlet(Servlet servlet, Map<String, String> config, String... patterns);

    TomcatApplicationBuilder addServlet(Servlet servlet, String name, Map<String, String> config, String... patterns);

    TomcatApplicationBuilder addApplicationParameter(ApplicationParameter parameter);

    TomcatApplicationBuilder addConstraint(SecurityConstraint constraint);

    TomcatApplicationBuilder addErrorPage(ErrorPage errorPage);

    TomcatApplicationBuilder addLocaleEncodingMappingParameter(String locale, String encoding);

    TomcatApplicationBuilder addMimeMapping(String extension, String mimeType);

    TomcatApplicationBuilder addParameter(String name, String value);

    TomcatApplicationBuilder addRoleMapping(String role, String link);

    TomcatApplicationBuilder addSecurityRole(String role);

    TomcatApplicationBuilder addWatchedResource(String name);

    TomcatApplicationBuilder addWelcomeFile(String name);

    TomcatApplicationBuilder setAllowCasualMultipartParsing(boolean allowCasualMultipartParsing);

    TomcatApplicationBuilder setSessionManager(Manager sessionManager);

    TomcatApplicationBuilder setConfigFile(URL configFile);

    TomcatApplicationBuilder setCookies(boolean cookies);

    TomcatApplicationBuilder setUseHttpOnly(boolean useHttpOnly);

    TomcatApplicationBuilder setSessionCookieName(String sessionCookieName);

    TomcatApplicationBuilder setSessionCookieDomain(String sessionCookieDomain);

    TomcatApplicationBuilder setSessionCookiePath(String sessionCookiePath);

    TomcatApplicationBuilder setSessionCookiePathUsesTrailingSlash(boolean sessionCookiePathUsesTrailingSlash);

    TomcatApplicationBuilder setCrossContext(boolean crossContext);

    TomcatApplicationBuilder setDistributable(boolean distributable);

    TomcatApplicationBuilder setLoginConfig(LoginConfig config);

    TomcatApplicationBuilder setReloadable(boolean reloadable);

    TomcatApplicationBuilder setPrivileged(boolean privileged);

    TomcatApplicationBuilder setSessionTimeout(int timeout);

    TomcatApplicationBuilder setSwallowAbortedUploads(boolean swallowAbortedUploads);

    TomcatApplicationBuilder setSwallowOutput(boolean swallowOutput);

    TomcatApplicationBuilder setXmlValidation(boolean xmlValidation);

    TomcatApplicationBuilder setXmlNamespaceAware(boolean xmlNamespaceAware);

    TomcatApplicationBuilder setTldValidation(boolean tldValidation);

    TomcatApplicationBuilder setTldNamespaceAware(boolean tldNamespaceAware);

    TomcatApplicationBuilder setResourceOnlyServlets(String resourceOnlyServlets);

    TomcatApplicationBuilder setEffectiveMajorVersion(int major);

    TomcatApplicationBuilder setEffectiveMinorVersion(int minor);

    TomcatApplicationBuilder setWebappVersion(String webappVersion);

    TomcatApplicationBuilder setFireRequestListenersOnForwards(boolean enable);

    TomcatApplicationBuilder setPreemptiveAuthentication(boolean enable);

    TomcatApplicationBuilder setSendRedirectBody(boolean enable);

}