package org.pidster.tomcat.embed.impl;

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
import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.startup.ContextConfig;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatApplicationBuilder;
import org.pidster.tomcat.embed.TomcatHostBuilder;


public class TomcatApplicationBuilderImpl extends AbstractContainerBuilder<TomcatHostBuilder, TomcatApplicationBuilder> implements TomcatApplicationBuilder {

    private final Context context;
    private final InternalContainerInitializer initializer;

    public TomcatApplicationBuilderImpl(TomcatHostBuilderImpl parent, Map<String, String> config) {
        super(parent);
        String className = "org.apache.catalina.core.StandardContext";
        this.context = InstanceConfigurer.instantiate(loader(), StandardContext.class, className, config);
        context.addLifecycleListener(new FixContextListener());
        StandardManager manager = new StandardManager();
        manager.setSecureRandomAlgorithm("SHA1PRNG");
        context.setManager(manager);

        this.initializer = new InternalContainerInitializer();
        context.addServletContainerInitializer(initializer, null);

        setContainer(context);
    }

    @Override
    public TomcatApplicationBuilder collect(Container child) {
        child.setParent(context);
        context.addChild(child);
        return this;
    }

    @Override
    public TomcatHostBuilder parent() {
        return super.parent().collect(context);
    }

    @Override
    public TomcatApplicationBuilder withDefaultConfig() {
        ContextConfig contextConfig = new ContextConfig();
        context.addLifecycleListener(contextConfig);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setContextAttribute(String attribute, Object value) {
        initializer.setContextAttribute(attribute, value);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setContextInitParameter(String initParameter, String value) {
        initializer.setContextInitParameter(initParameter, value);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addServletContainerInitializer(Class<? extends ServletContainerInitializer> listenerClass) {
        return addServletContainerInitializer(listenerClass, null);
    }

    @Override
    public TomcatApplicationBuilder addServletContainerInitializer(Class<? extends ServletContainerInitializer> listenerClass, Set<Class<?>> classes) {
        try {
            ServletContainerInitializer instance = listenerClass.newInstance();
            return addServletContainerInitializer(instance, classes);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public TomcatApplicationBuilder addServletContainerInitializer(ServletContainerInitializer sci) {
        return addServletContainerInitializer(sci, null);
    }

    @Override
    public TomcatApplicationBuilder addServletContainerInitializer(ServletContainerInitializer sci, Set<Class<?>> classes) {
        context.addServletContainerInitializer(sci, classes);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addServletContextListener(Class<? extends ServletContextListener> listenerClass) {
        return addServletContextListener(listenerClass, Tomcat.EMPTY);
    }

    @Override
    public TomcatApplicationBuilder addServletContextListener(Class<? extends ServletContextListener> listenerClass, Map<String, String> config) {
        try {
            ServletContextListener instance = listenerClass.newInstance();
            return addServletContextListener(instance);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public TomcatApplicationBuilder addServletContextListener(ServletContextListener listener) {
        initializer.add(listener);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addServletFilter(Filter filter, String... urlPatterns) {
        FilterHolder holder = new FilterHolder(filter, urlPatterns);
        initializer.add(holder);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addServletFilter(Filter filter, Map<String, String> initParameters, String... urlPatterns) {
        FilterHolder holder = new FilterHolder(filter, initParameters, urlPatterns);
        initializer.add(holder);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addServletFilter(Filter filter, Map<String, String> initParameters, EnumSet<DispatcherType> dispatcherTypes, String... urlPatterns) {
        FilterHolder holder = new FilterHolder(filter, initParameters, dispatcherTypes, urlPatterns);
        initializer.add(holder);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addServletFilter(Class<? extends Filter> filterClass, String... patterns) {
        return addServletFilter(filterClass, Tomcat.EMPTY, patterns);
    }

    @Override
    public TomcatApplicationBuilder addServletFilter(Class<? extends Filter> filterClass, Map<String, String> initParameters, String... patterns) {
        try {
            Filter instance = filterClass.newInstance();
            return addServletFilter(instance, patterns);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, String... patterns) {
        return addServlet(servletClass.getName(), servletClass, Tomcat.EMPTY, patterns);
    }

    @Override
    public TomcatApplicationBuilder addServlet(String name, Class<? extends Servlet> servletClass, String... patterns) {
        return addServlet(name, servletClass, Tomcat.EMPTY, patterns);
    }

    @Override
    public TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, Map<String, String> config, String... patterns) {
        return addServlet(servletClass.getName(), servletClass, config, patterns);
    }

    @Override
    public TomcatApplicationBuilder addServlet(String name, Class<? extends Servlet> servletClass, Map<String, String> config, String... patterns) {
        try {
            Servlet instance = servletClass.newInstance();
            return addServlet(instance, name, config, patterns);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public TomcatApplicationBuilder addServlet(Servlet servlet, Map<String, String> config, String... patterns) {
        initializer.add(new ServletHolder(servlet, config, patterns));
        return this;
    }

    @Override
    public TomcatApplicationBuilder addServlet(Servlet servlet, String servletName, Map<String, String> config, String... patterns) {
        initializer.add(new ServletHolder(servlet, servletName, config, patterns));
        return this;
    }

    @Override
    public TomcatApplicationBuilder setAllowCasualMultipartParsing(boolean allowCasualMultipartParsing) {
        context.setAllowCasualMultipartParsing(allowCasualMultipartParsing);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setConfigFile(URL configFile) {
        context.setConfigFile(configFile);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setSessionManager(Manager sessionManager) {
        context.setManager(sessionManager);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setCookies(boolean cookies) {
        context.setCookies(cookies);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setUseHttpOnly(boolean useHttpOnly) {
        context.setUseHttpOnly(useHttpOnly);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setSessionCookieName(String sessionCookieName) {
        context.setSessionCookieName(sessionCookieName);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setSessionCookieDomain(String sessionCookieDomain) {
        context.setSessionCookieDomain(sessionCookieDomain);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setSessionCookiePath(String sessionCookiePath) {
        context.setSessionCookiePath(sessionCookiePath);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setSessionCookiePathUsesTrailingSlash(boolean sessionCookiePathUsesTrailingSlash) {
        context.setSessionCookiePathUsesTrailingSlash(sessionCookiePathUsesTrailingSlash);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setCrossContext(boolean crossContext) {
        context.setCrossContext(crossContext);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setDistributable(boolean distributable) {
        context.setDistributable(distributable);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setLoginConfig(LoginConfig config) {
        context.setLoginConfig(config);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setReloadable(boolean reloadable) {
        context.setReloadable(reloadable);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setPrivileged(boolean privileged) {
        context.setPrivileged(privileged);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setSessionTimeout(int timeout) {
        context.setSessionTimeout(timeout);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setSwallowAbortedUploads(boolean swallowAbortedUploads) {
        context.setSwallowAbortedUploads(swallowAbortedUploads);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setSwallowOutput(boolean swallowOutput) {
        context.setSwallowOutput(swallowOutput);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setXmlValidation(boolean xmlValidation) {
        context.setXmlValidation(xmlValidation);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setXmlNamespaceAware(boolean xmlNamespaceAware) {
        context.setXmlNamespaceAware(xmlNamespaceAware);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setTldValidation(boolean tldValidation) {
        context.setTldValidation(tldValidation);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setTldNamespaceAware(boolean tldNamespaceAware) {
        context.setTldNamespaceAware(tldNamespaceAware);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addApplicationParameter(ApplicationParameter parameter) {
        context.addApplicationParameter(parameter);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addConstraint(SecurityConstraint constraint) {
        context.addConstraint(constraint);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addErrorPage(ErrorPage errorPage) {
        context.addErrorPage(errorPage);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addLocaleEncodingMappingParameter(String locale, String encoding) {
        context.addLocaleEncodingMappingParameter(locale, encoding);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addMimeMapping(String extension, String mimeType) {
        context.addMimeMapping(extension, mimeType);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addParameter(String name, String value) {
        context.addParameter(name, value);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addRoleMapping(String role, String link) {
        context.addRoleMapping(role, link);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addSecurityRole(String role) {
        context.addSecurityRole(role);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addWatchedResource(String name) {
        context.addWatchedResource(name);
        return this;
    }

    @Override
    public TomcatApplicationBuilder addWelcomeFile(String name) {
        context.addWelcomeFile(name);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setResourceOnlyServlets(String resourceOnlyServlets) {
        context.setResourceOnlyServlets(resourceOnlyServlets);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setEffectiveMajorVersion(int major) {
        context.setEffectiveMajorVersion(major);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setEffectiveMinorVersion(int minor) {
        context.setEffectiveMinorVersion(minor);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setWebappVersion(String webappVersion) {
        context.setWebappVersion(webappVersion);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setFireRequestListenersOnForwards(boolean enable) {
        context.setFireRequestListenersOnForwards(enable);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setPreemptiveAuthentication(boolean enable) {
        context.setPreemptiveAuthentication(enable);
        return this;
    }

    @Override
    public TomcatApplicationBuilder setSendRedirectBody(boolean enable) {
        context.setSendRedirectBody(enable);
        return this;
    }

}
