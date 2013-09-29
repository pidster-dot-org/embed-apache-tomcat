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
package org.pidster.tomcat.embed.impl;

import static org.pidster.tomcat.embed.Tomcat.*;
import static org.pidster.tomcat.embed.impl.Implementations.*;

import java.io.File;
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
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Loader;
import org.apache.catalina.Manager;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.startup.ContextConfig;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.JarScanner;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.pidster.tomcat.embed.TomcatApplicationBuilder;
import org.pidster.tomcat.embed.TomcatHostBuilder;

/**
 * 
 * @author swilliams
 *
 */
public class TomcatApplicationBuilderImpl extends AbstractContainerBuilder<TomcatHostBuilder, TomcatApplicationBuilder> implements TomcatApplicationBuilder {

    private final Context context;

    private final InternalContainerInitializer initializer;

    private boolean makeDirs = false;

    /**
     * @param parent
     * @param config
     */
    public TomcatApplicationBuilderImpl(TomcatHostBuilderImpl parent, Map<String, String> config) {
        super(parent);
        this.context = InstanceConfigurer.instantiate(loader(), StandardContext.class, CONTEXT, config);
        context.addLifecycleListener(new FixContextListener());
        StandardManager manager = new StandardManager();
        manager.setSecureRandomAlgorithm("SHA1PRNG");
        manager.setContext(context);
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
        TomcatHostBuilder parent = super.parent().collect(context);

        if (makeDirs) {
            // MUST to do this only after collection
            Host host = (Host) context.getParent();
            File appBase = new File(host.getAppBase());
            File docBase = new File(appBase, context.getPath());
            docBase.mkdirs();
        }

        return parent;
    }

    @Override
    public TomcatApplicationBuilder withDefaultConfig() {
        ContextConfig contextConfig = new ContextConfig();
        context.addLifecycleListener(contextConfig);
        return this;
    }

    @Override
    public TomcatApplicationBuilder makeDirs() {
        this.makeDirs = true;
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
        ServletContainerInitializer instance = InstanceConfigurer.newInstance(listenerClass);
        return addServletContainerInitializer(instance, classes);
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
        return addServletContextListener(listenerClass, EMPTY_MAP);
    }

    @Override
    public TomcatApplicationBuilder addServletContextListener(Class<? extends ServletContextListener> listenerClass, Map<String, String> config) {
        ServletContextListener instance = InstanceConfigurer.newInstance(listenerClass);
        return addServletContextListener(instance);
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
        return addServletFilter(filterClass, EMPTY_MAP, patterns);
    }

    @Override
    public TomcatApplicationBuilder addServletFilter(Class<? extends Filter> filterClass, Map<String, String> initParameters, String... patterns) {
        Filter instance = InstanceConfigurer.newInstance(filterClass);
        return addServletFilter(instance, patterns);
    }

    @Override
    public TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, String... patterns) {
        return addServlet(servletClass.getName(), servletClass, EMPTY_MAP, patterns);
    }

    @Override
    public TomcatApplicationBuilder addServlet(String name, Class<? extends Servlet> servletClass, String... patterns) {
        return addServlet(name, servletClass, EMPTY_MAP, patterns);
    }

    @Override
    public TomcatApplicationBuilder addServlet(Class<? extends Servlet> servletClass, Map<String, String> config, String... patterns) {
        return addServlet(servletClass.getName(), servletClass, config, patterns);
    }

    @Override
    public TomcatApplicationBuilder addServlet(String name, Class<? extends Servlet> servletClass, Map<String, String> config, String... patterns) {
        Servlet instance = InstanceConfigurer.newInstance(servletClass);
        return addServlet(instance, name, config, patterns);
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
    	sessionManager.setContext(context);
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
    public TomcatApplicationBuilder setDenyUncoveredHttpMethods(boolean denyUncoveredHttpMethods) {
        context.setDenyUncoveredHttpMethods(denyUncoveredHttpMethods);
        return this;
    }

	@Override
    public TomcatApplicationBuilder setDocBase(String docBase) {
		context.setDocBase(docBase);
        return this;
	}

	@Override
	public TomcatApplicationBuilder setIgnoreAnnotations(boolean ignoreAnnotations) {
		context.setIgnoreAnnotations(ignoreAnnotations);
        return this;
	}

	@Override
	public TomcatApplicationBuilder setJarScanner(JarScanner jarScanner) {
		context.setJarScanner(jarScanner);
        return this;
	}

	@Override
	public TomcatApplicationBuilder setLogEffectiveWebXml(boolean logEffectiveWebXml) {
		context.setLogEffectiveWebXml(logEffectiveWebXml);
        return this;
	}

	@Override
	public TomcatApplicationBuilder setInstanceManager(InstanceManager instanceManager) {
		context.setInstanceManager(instanceManager);
        return this;
	}

	@Override
	public TomcatApplicationBuilder setJspConfigDescriptor(JspConfigDescriptor descriptor) {
		context.setJspConfigDescriptor(descriptor);
        return this;
	}

	@Override
	public TomcatApplicationBuilder setLoader(Loader loader) {
		context.setLoader(loader);
        return this;
	}

	@Override
	public TomcatApplicationBuilder setResources(WebResourceRoot resources) {
		context.setResources(resources);
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
