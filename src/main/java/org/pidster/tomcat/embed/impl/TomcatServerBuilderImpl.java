package org.pidster.tomcat.embed.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatBuilder;
import org.pidster.tomcat.embed.TomcatServerBuilder;
import org.pidster.tomcat.embed.TomcatServiceBuilder;


public class TomcatServerBuilderImpl extends AbstractLifecycleBuilder<TomcatBuilder, TomcatServerBuilder> implements Builder<Tomcat>, TomcatServerBuilder {

    private final Server server;

    public TomcatServerBuilderImpl(TomcatBuilderImpl parent, Map<String, String> config) {
        super(parent);

        String className = "org.apache.catalina.core.StandardServer";

        this.server = InstanceConfigurer.instantiate(loader(), Server.class, className, config);
    }

    @Override
    public TomcatServerBuilder collect(Service child) {
        child.setServer(server);
        server.addService(child);
        return this;
    }

    @Override
    public TomcatBuilder parent() {
        return super.parent().collect(server);
    }

    @Override
    public TomcatServerBuilder setCatalinaBase(File catalinaBase) {
        server.setCatalinaBase(catalinaBase);
        return this;
    }

    @Override
    public TomcatServerBuilder setCatalinaHome(File catalinaHome) {
        server.setCatalinaBase(catalinaHome);
        return this;
    }

    @Override
    public TomcatServiceBuilder addService(String name) {
        Map<String, String> config = new HashMap<>();
        config.put("name", name);
        return new TomcatServiceBuilderImpl(this, name, config);
    }

    @Override
    public TomcatServiceBuilder addService(String name, String jvmRoute) {
        Map<String, String> config = new HashMap<>();
        config.put("name", name);
        config.put("jvmRoute", jvmRoute);
        return new TomcatServiceBuilderImpl(this, name, config);
    }

}
