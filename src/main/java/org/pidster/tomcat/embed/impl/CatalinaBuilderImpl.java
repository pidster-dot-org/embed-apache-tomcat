package org.pidster.tomcat.embed.impl;

import static org.pidster.tomcat.embed.Tomcat.EMPTY;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.catalina.Server;
import org.apache.catalina.core.JasperListener;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.apache.catalina.core.ThreadLocalLeakPreventionListener;
import org.apache.catalina.deploy.ResourceBase;
import org.apache.catalina.mbeans.GlobalResourcesLifecycleListener;
import org.apache.catalina.startup.Catalina;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.CatalinaBuilder;
import org.pidster.tomcat.embed.TomcatHostBuilder;
import org.pidster.tomcat.embed.TomcatServerBuilder;


public class CatalinaBuilderImpl extends AbstractHierarchicalBuilder<CatalinaBuilderImpl, CatalinaBuilderImpl> implements CatalinaBuilder {

    private final Catalina catalina = new Catalina();

    private final Properties properties = new Properties();

    public CatalinaBuilderImpl() {
        super(null);
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
    public CatalinaBuilder setProperties(Properties properties) {
        properties.putAll(properties);
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
        return newServer(8005);
    }

    @Override
    public TomcatServerBuilder newServer(int port) {
        return newServer("localhost", port);
    }

    @Override
    public TomcatServerBuilder newServer(String host, int port) {
        return newServer(host, port, "SHUTDOWN");
    }

    @Override
    public TomcatServerBuilder newServer(int port, String password) {
        return newServer("localhost", port, password);
    }

    @Override
    public TomcatHostBuilder newStandardServer(File baseDir) {
        return newStandardServer(-1, baseDir);
    }

    @Override
    public TomcatHostBuilder newStandardServer(int port, File baseDir) {
        ResourceBase resource = new ResourceBase();
        return newServer(port)
                .setCatalinaBase(baseDir)
                .setCatalinaHome(baseDir)
                .addLifecycleListener(JasperListener.class)
                .addLifecycleListener(JreMemoryLeakPreventionListener.class)
                .addLifecycleListener(GlobalResourcesLifecycleListener.class)
                .addLifecycleListener(ThreadLocalLeakPreventionListener.class)
                .addGlobalResource(resource)
                .addService("Catalina")
                    // .withDefaultRealm()
                    .setBackgroundProcessorDelay(0)
                    .setStartStopThreads(0)
                    .addExecutor("embed-pool-1", "tomcat-exec1-", 200, 5, EMPTY)
                    .addConnector("HTTP/1.1", 8090, EMPTY)
                    .addConnector("AJP/1.3", 8019, EMPTY)
                        .addHost("localhost", "webapps");
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

}
