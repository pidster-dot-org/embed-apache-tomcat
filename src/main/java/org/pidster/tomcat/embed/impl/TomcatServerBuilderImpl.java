package org.pidster.tomcat.embed.impl;

import static org.pidster.tomcat.embed.Tomcat.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.core.NamingContextListener;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.deploy.NamingResources;
import org.pidster.tomcat.embed.CatalinaBuilder;
import org.pidster.tomcat.embed.TomcatServerBuilder;
import org.pidster.tomcat.embed.TomcatServiceBuilder;


public class TomcatServerBuilderImpl extends AbstractLifecycleBuilder<CatalinaBuilder, TomcatServerBuilder> implements TomcatServerBuilder {

    private final Server server;

    static final String[] silences = new String[] {
        "org.apache.coyote.AbstractProtocol",
        "org.apache.coyote.ajp.AjpNioProtocol",
        "org.apache.coyote.http11.Http11Protocol",
        "org.apache.coyote.http11.Http11NioProtocol",
        "org.apache.catalina.core.ApplicationContext",
        "org.apache.catalina.core.AprLifecycleListener",
        "org.apache.catalina.core.StandardService",
        "org.apache.catalina.core.StandardEngine",
        "org.apache.catalina.mbeans.GlobalResourcesLifecycleListener",
        "org.apache.catalina.startup.Catalina",
        "org.apache.catalina.startup.ContextConfig",
        "org.apache.tomcat.util.net.NioSelectorPool",
    };

    private boolean silentLogging = true;

    private boolean enableNaming = false;

    public TomcatServerBuilderImpl(CatalinaBuilderImpl parent, Map<String, String> config) {
        super(parent);

        String className = "org.apache.catalina.core.StandardServer";
        this.server = InstanceConfigurer.instantiate(loader(), Server.class, className, config);

        for (String s : silences) {
            if (silentLogging) {
                Logger.getLogger(s).setLevel(Level.WARNING);
            } else {
                // Logger.getLogger(s).setLevel(Level.INFO);
            }
        }

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
                setCatalinaBase(file);
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
                    // if we reach here, this must be set already
                    this.setCatalinaHome(server.getCatalinaBase());
                }
            }
        }

        for (String s : silences) {
            if (silentLogging) {
                Logger.getLogger(s).setLevel(Level.WARNING);
            } else {
                Logger.getLogger(s).setLevel(Level.INFO);
            }
        }

        return super.parent().collect(server);
    }

    @Override
    public TomcatServerBuilder setSilentLogging(boolean silentLogging) {
        this.silentLogging = silentLogging;
        return this;
    }

    @Override
    public TomcatServerBuilder setCatalinaBase(File catalinaBase) {
        if (catalinaBase == null || !catalinaBase.exists()) {
            throw new IllegalStateException("catalina.base does not exist: " + catalinaBase);
        }

        System.setProperty("catalina.base", catalinaBase.getAbsolutePath());
        server.setCatalinaBase(catalinaBase);
        return this;
    }

    @Override
    public TomcatServerBuilder setCatalinaHome(File catalinaHome) {
        if (catalinaHome == null || !catalinaHome.exists()) {
            throw new IllegalStateException("catalina.home does not exist: " + catalinaHome);
        }

        System.setProperty("catalina.home", catalinaHome.getAbsolutePath());
        server.setCatalinaBase(catalinaHome);
        return this;
    }

    @Override
    public TomcatServerBuilder enableNaming() {
        return setEnableNaming(true);
    }

    @Override
    public TomcatServerBuilder setEnableNaming(boolean enableNaming) {
        this.enableNaming = enableNaming;
        if (enableNaming) {
            enableJndi();
        }
        return this;
    }

    @Override
    public TomcatServerBuilder addGlobalResource(ContextResource resource) {
        if (!enableNaming) {
            enableJndi();
        }

        NamingResources globalNamingResources = new NamingResources();
        // globalNamingResources.addResource(resource);
        globalNamingResources.setContainer(server);
        server.setGlobalNamingResources(globalNamingResources);
        return this;
    }

    @Override
    public TomcatServiceBuilder addService() {
        return addService(DEFAULT_SERVICE_NAME, null);
    }

    @Override
    public TomcatServiceBuilder addService(String jvmRoute) {
        return addService(DEFAULT_SERVICE_NAME, jvmRoute);
    }

    @Override
    public TomcatServiceBuilder addService(String name, String jvmRoute) {
        Map<String, String> config = new HashMap<>();
        config.put("name", name);
        if (jvmRoute != null) {
            config.put("jvmRoute", jvmRoute);
        }
        return new TomcatServiceBuilderImpl(this, name, config);
    }

    private void enableJndi() {
        server.addLifecycleListener(new NamingContextListener());

        System.setProperty("catalina.useNaming", "true");

        String value = "org.apache.naming";
        String oldValue =
            System.getProperty(javax.naming.Context.URL_PKG_PREFIXES);
        if (oldValue != null) {
            if (oldValue.contains(value)) {
                value = oldValue;
            } else {
                value = value + ":" + oldValue;
            }
        }
        System.setProperty(javax.naming.Context.URL_PKG_PREFIXES, value);

        value = System.getProperty
            (javax.naming.Context.INITIAL_CONTEXT_FACTORY);
        if (value == null) {
            System.setProperty
                (javax.naming.Context.INITIAL_CONTEXT_FACTORY,
                 "org.apache.naming.java.javaURLContextFactory");
        }

        this.enableNaming = true;
    }

}
