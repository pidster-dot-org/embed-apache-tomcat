package org.pidster.tomcat.embed.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.deploy.ResourceBase;
import org.pidster.tomcat.embed.CatalinaBuilder;
import org.pidster.tomcat.embed.TomcatServerBuilder;
import org.pidster.tomcat.embed.TomcatServiceBuilder;


public class TomcatServerBuilderImpl extends AbstractLifecycleBuilder<CatalinaBuilder, TomcatServerBuilder> implements TomcatServerBuilder {

    private final Server server;

    static final String[] silences = new String[] {
        "org.apache.coyote.AbstractProtocol",
        "org.apache.coyote.http11.Http11Protocol",
        "org.apache.catalina.core.StandardService",
        "org.apache.catalina.core.StandardEngine",
        "org.apache.catalina.startup.ContextConfig",
        "org.apache.catalina.core.ApplicationContext",
        "org.apache.catalina.core.AprLifecycleListener"
    };

    private boolean silentLogging = true;

    public TomcatServerBuilderImpl(CatalinaBuilderImpl parent, Map<String, String> config) {
        super(parent);

        String className = "org.apache.catalina.core.StandardServer";
        this.server = InstanceConfigurer.instantiate(loader(), Server.class, className, config);

        setLifecycle(server);
    }

    @Override
    public TomcatServerBuilder collect(Service child) {
        child.setServer(server);
        server.addService(child);
        return this;
    }

    @Override
    public CatalinaBuilder parent() {

        if (server.getCatalinaBase() == null || !server.getCatalinaBase().exists()) {
            if (System.getProperties().containsKey("catalina.base")) {
                String catalinaBase = System.getProperty("catalina.base");
                File file = new File(catalinaBase);
                this.setCatalinaBase(file);
            }
        }

        if (server.getCatalinaHome() == null || !server.getCatalinaHome().exists()) {
            if (System.getProperties().containsKey("catalina.home")) {
                String catalinaBase = System.getProperty("catalina.home");
                File file = new File(catalinaBase);
                if (file.exists()) {
                    this.setCatalinaHome(file);
                }
                else {
                    this.setCatalinaHome(server.getCatalinaBase());
                }
            }
        }

        for (String s : silences) {
            if (silentLogging) {
                Logger.getLogger(s).setLevel(Level.SEVERE);
            } else {
                // Logger.getLogger(s).setLevel(Level.INFO);
            }
        }

        System.out.println("FOO!");
        Thread.dumpStack();

        return super.parent().collect(server);
    }

    @Override
    public TomcatServerBuilder setSilentLogging(boolean silentLogging) {
        this.silentLogging = silentLogging;
        return this;
    }

    @Override
    public TomcatServerBuilder setCatalinaBase(File catalinaBase) {
        if (!catalinaBase.exists()) {
            throw new IllegalStateException("catalina.base MUST exist");
        }

        System.setProperty("catalina.base", catalinaBase.getAbsolutePath());
        server.setCatalinaBase(catalinaBase);
        return this;
    }

    @Override
    public TomcatServerBuilder setCatalinaHome(File catalinaHome) {
        if (!catalinaHome.exists()) {
            throw new IllegalStateException("catalina.home MUST exist");
        }

        System.setProperty("catalina.home", catalinaHome.getAbsolutePath());
        server.setCatalinaBase(catalinaHome);
        return this;
    }

    @Override
    public TomcatServerBuilder addGlobalResource(ResourceBase resource) {
        // TODO
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
