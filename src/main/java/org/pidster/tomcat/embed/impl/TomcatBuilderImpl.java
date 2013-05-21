package org.pidster.tomcat.embed.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.catalina.Server;
import org.apache.catalina.startup.Catalina;
import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatBuilder;
import org.pidster.tomcat.embed.TomcatServerBuilder;


public class TomcatBuilderImpl extends AbstractParentalBuilder<TomcatBuilderImpl, TomcatBuilderImpl> implements Builder<Tomcat>, TomcatBuilder {

    private final Catalina catalina = new Catalina();

    private final Properties properties = new Properties();

    public TomcatBuilderImpl() {
        super(null);
    }

    @Override
    public TomcatBuilder collect(Server child) {
        child.setCatalina(catalina);
        catalina.setServer(child);
        return this;
    }

    @Override
    public Tomcat build() {
        return new TomcatRuntimeImpl(catalina);
    }

    @Override
    public TomcatBuilder setProperties(Properties properties) {
        properties.putAll(properties);
        return this;
    }

    @Override
    public TomcatBuilder setProperty(String name, String value) {
        properties.setProperty(name, value);
        return this;
    }

    @Override
    public TomcatBuilder useConfig(String file) {
        return useConfig(new File(file));
    }

    @Override
    public TomcatBuilder useConfig(File file) {
        if (!file.exists()) {
            throw new IllegalStateException("Config file does not exist");
        }
        catalina.setConfigFile(file.getAbsolutePath());
        return this;
    }

    @Override
    public TomcatBuilder useClassLoader(ClassLoader classLoader) {
        catalina.setParentClassLoader(classLoader);
        return this;
    }

    @Override
    public TomcatBuilder setUseShutdownHook(boolean useShutdownHook) {
        catalina.setUseShutdownHook(useShutdownHook);
        return this;
    }

    @Override
    public TomcatBuilder setUseNaming(boolean useNaming) {
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
    public TomcatServerBuilder newServer(String host, int port, String password) {

        System.getProperties().putAll(properties);

        Map<String, String> config = new HashMap<>();
        config.put("address", host);
        config.put("port", String.valueOf(port));
        config.put("shutdown", password);
        return new TomcatServerBuilderImpl(this, config);
    }

}
