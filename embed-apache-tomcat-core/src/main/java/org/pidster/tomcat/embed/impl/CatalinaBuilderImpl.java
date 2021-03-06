/*
   Copyright 2013 pidster

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

import static org.pidster.tomcat.embed.Tomcat.DEFAULT_AJP_PORT;
import static org.pidster.tomcat.embed.Tomcat.DEFAULT_EXECUTOR_MAX;
import static org.pidster.tomcat.embed.Tomcat.DEFAULT_EXECUTOR_MIN;
import static org.pidster.tomcat.embed.Tomcat.DEFAULT_EXECUTOR_NAME;
import static org.pidster.tomcat.embed.Tomcat.DEFAULT_HTTP_PORT;
import static org.pidster.tomcat.embed.Tomcat.DEFAULT_SERVICE_NAME;
import static org.pidster.tomcat.embed.Tomcat.DEFAULT_SHUTDOWN_PORT;
import static org.pidster.tomcat.embed.Tomcat.EMPTY_MAP;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.catalina.Server;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.apache.catalina.core.ThreadLocalLeakPreventionListener;
import org.apache.catalina.mbeans.GlobalResourcesLifecycleListener;
import org.apache.catalina.startup.Catalina;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.pidster.tomcat.embed.CatalinaBuilder;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatHostBuilder;
import org.pidster.tomcat.embed.TomcatServerBuilder;

/**
 * @author pidster
 * 
 */
public class CatalinaBuilderImpl extends AbstractHierarchicalBuilder<CatalinaBuilderImpl, CatalinaBuilderImpl> implements CatalinaBuilder {

    public static final String SHUTDOWN_COMMAND = "SHUTDOWN";

    public static final String LOCALHOST = "localhost";

    private final Catalina catalina = new Catalina();

    private final Properties properties = new Properties();

    public CatalinaBuilderImpl() {
        super(null);
        this.properties.setProperty("catalina.useNaming", "false");
    }

    @Override
    public CatalinaBuilder collect(Server child) {
        child.setCatalina(catalina);
        catalina.setServer(child);
        return this;
    }

    @Override
    public Tomcat build() {
        return new TomcatRuntimeImpl(catalina);
    }

    @Override
    public CatalinaBuilder addProperties(Properties properties) {
        this.properties.putAll(properties);
        return this;
    }

    @Override
    public CatalinaBuilder setProperty(String name, String value) {
        properties.setProperty(name, value);
        return this;
    }

    @Override
    public CatalinaBuilder useConfig(String file) {
        return useConfig(new File(file));
    }

    @Override
    public CatalinaBuilder useConfig(File file) {
        if (!file.exists()) {
            throw new IllegalStateException("Config file does not exist");
        }
        catalina.setConfigFile(file.getAbsolutePath());
        return this;
    }

    @Override
    public CatalinaBuilder useClassLoader(ClassLoader classLoader) {
        catalina.setParentClassLoader(classLoader);
        return this;
    }

    @Override
    public CatalinaBuilder setUseShutdownHook(boolean useShutdownHook) {
        catalina.setUseShutdownHook(useShutdownHook);
        return this;
    }

    @Override
    public CatalinaBuilder setUseNaming(boolean useNaming) {
        catalina.setUseNaming(useNaming);
        return this;
    }

    @Override
    public TomcatServerBuilder newServer() {
        return newServer(DEFAULT_SHUTDOWN_PORT);
    }

    @Override
    public TomcatServerBuilder newServer(int port) {
        return newServer(LOCALHOST, port);
    }

    @Override
    public TomcatServerBuilder newServer(String host, int port) {
        return newServer(host, port, SHUTDOWN_COMMAND);
    }

    @Override
    public TomcatServerBuilder newServer(int port, String password) {
        return newServer(LOCALHOST, port, password);
    }

    @Override
    public TomcatServerBuilder newServer(String host, int port, String password) {

        System.getProperties().putAll(properties);

        Map<String, String> config = new HashMap<>();
        config.put("address", host);
        config.put("port", String.valueOf(port));
        config.put("shutdown", password);

        return new TomcatServerBuilderImpl(this, config);
    }

    @Override
    public TomcatHostBuilder newStandardServer() {
        return newStandardServer(-1, null, DEFAULT_HTTP_PORT, DEFAULT_AJP_PORT);
    }

    @Override
    public TomcatHostBuilder newStandardServer(File baseDir) {
        return newStandardServer(-1, baseDir, DEFAULT_HTTP_PORT, DEFAULT_AJP_PORT);
    }

    @Override
    public TomcatHostBuilder newStandardServer(File baseDir, int httpPort, int ajpPort) {
        return newStandardServer(-1, baseDir, httpPort, ajpPort);
    }

    @Override
    public TomcatHostBuilder newStandardServer(int httpPort, int ajpPort) {
        return newStandardServer(-1, null, httpPort, ajpPort);
    }

    @Override
    public TomcatHostBuilder newStandardServer(int port, File baseDir) {
        return newStandardServer(port, baseDir, DEFAULT_HTTP_PORT, DEFAULT_AJP_PORT);
    }

    @Override
    public TomcatHostBuilder newStandardServer(int port, File baseDir, int httpPort, int ajpPort) {
        ContextResource memoryDatabase = new ContextResource();
        memoryDatabase.setName("name");
        memoryDatabase.setDescription("desc");

        TomcatServerBuilder serverBuilder = newServer(port);
        if (baseDir != null) {
            serverBuilder.setCatalinaBase(baseDir);
            serverBuilder.setCatalinaHome(baseDir);
        }

        Map<String, String> connConfig = new HashMap<>();
        connConfig.put(Constants.EXECUTOR_NAME_ATTR, DEFAULT_EXECUTOR_NAME);

        return serverBuilder.enableNaming()
        			// .addLifecycleListener(SecurityListener.class)
        			.addLifecycleListener(AprLifecycleListener.class)
        			.addLifecycleListener(JreMemoryLeakPreventionListener.class)
        			.addLifecycleListener(GlobalResourcesLifecycleListener.class)
        			.addLifecycleListener(ThreadLocalLeakPreventionListener.class)
        			.addGlobalResource(memoryDatabase)
                .addService(DEFAULT_SERVICE_NAME)
                // TODO .withDefaultRealm()
                .setBackgroundProcessorDelay(0)
                .setStartStopThreads(0)
                .addExecutor(DEFAULT_EXECUTOR_NAME, "tomcat-exec-", DEFAULT_EXECUTOR_MIN, DEFAULT_EXECUTOR_MAX, EMPTY_MAP)
                .addConnector(Tomcat.PROTOCOL_BIO, httpPort, connConfig)
                .addConnector(Tomcat.PROTOCOL_AJP, ajpPort, connConfig)
                .addHost(LOCALHOST, "webapps");
    }

    @Override
    public TomcatHostBuilder newMinimalServer() {
        return newMinimalServer(-1, null, DEFAULT_HTTP_PORT);
    }

    @Override
    public TomcatHostBuilder newMinimalServer(int http) {
        return newMinimalServer(-1, null, http);
    }

    @Override
    public TomcatHostBuilder newMinimalServer(File baseDir) {
        return newMinimalServer(-1, baseDir, DEFAULT_HTTP_PORT);
    }

    @Override
    public TomcatHostBuilder newMinimalServer(File baseDir, int http) {
        return newMinimalServer(-1, baseDir, http);
    }

    @Override
    public TomcatHostBuilder newMinimalServer(int port, File baseDir) {
        return newMinimalServer(port, baseDir, DEFAULT_HTTP_PORT);
    }

    @Override
    public TomcatHostBuilder newMinimalServer(int port, File baseDir, int http) {
        TomcatServerBuilder serverBuilder = newServer(port);
        if (baseDir != null) {
            serverBuilder.setCatalinaBase(baseDir);
            serverBuilder.setCatalinaHome(baseDir);
        }

        Map<String, String> connConfig = new HashMap<>();
        connConfig.put(Constants.EXECUTOR_NAME_ATTR, DEFAULT_EXECUTOR_NAME);

        return serverBuilder.addService(DEFAULT_SERVICE_NAME).setBackgroundProcessorDelay(0).setStartStopThreads(0).addExecutor(DEFAULT_EXECUTOR_NAME, "tomcat-exec-", DEFAULT_EXECUTOR_MIN, DEFAULT_EXECUTOR_MAX, EMPTY_MAP).addConnector(Tomcat.PROTOCOL_BIO, http, connConfig).addHost(LOCALHOST, "webapps");
    }

}
