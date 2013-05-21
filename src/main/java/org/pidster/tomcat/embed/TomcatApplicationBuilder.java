package org.pidster.tomcat.embed;

import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContextListener;

import org.apache.catalina.Container;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityConstraint;


public interface TomcatApplicationBuilder extends Builder<Tomcat>, ContainerBuilder<TomcatHostBuilder, TomcatApplicationBuilder>, Collector<TomcatApplicationBuilder, Container> {

    TomcatHostBuilder parent();

    TomcatApplicationBuilder withDefaultConfig();

    TomcatApplicationBuilder addServletContainerInitializer(Class<? extends ServletContainerInitializer> listenerClass, Set<Class<?>> classes);

    TomcatApplicationBuilder addServletContainerInitializer(Class<? extends ServletContainerInitializer> listenerClass, Set<Class<?>> classes, Map<String, String> config);

    TomcatApplicationBuilder addServletContainerInitializer(ServletContainerInitializer sci, Set<Class<?>> classes);

    TomcatApplicationBuilder addServletContextListener(Class<? extends ServletContextListener> listenerClass);

    TomcatApplicationBuilder addServletContextListener(Class<? extends ServletContextListener> listenerClass, Map<String, String> config);

    TomcatApplicationBuilder addServletContextListener(ServletContextListener listener);

    TomcatApplicationBuilder addServletFilter(Class<? extends Filter> filterClass, String... mappings);

    TomcatApplicationBuilder addServletFilter(Class<? extends Filter> filterClass, Map<String, String> config, String... mappings);

    TomcatApplicationBuilder addServletFilter(Filter filter, String... patterns);

    TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, String... mappings);

    TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, Map<String, String> config, String... mappings);

    TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, String name, Map<String, String> config, String... mappings);

    TomcatApplicationBuilder addServlet(Servlet servlet, Map<String, String> config, String... patterns);

    TomcatApplicationBuilder addServlet(Servlet servlet, String name, Map<String, String> config, String... patterns);

    TomcatApplicationBuilder setAllowCasualMultipartParsing(boolean allowCasualMultipartParsing);

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

    TomcatApplicationBuilder setResourceOnlyServlets(String resourceOnlyServlets);

    TomcatApplicationBuilder setEffectiveMajorVersion(int major);

    TomcatApplicationBuilder setEffectiveMinorVersion(int minor);

    TomcatApplicationBuilder setWebappVersion(String webappVersion);

    TomcatApplicationBuilder setFireRequestListenersOnForwards(boolean enable);

    TomcatApplicationBuilder setPreemptiveAuthentication(boolean enable);

    TomcatApplicationBuilder setSendRedirectBody(boolean enable);

}