package org.pidster.tomcat.embed.impl;

import static org.pidster.tomcat.embed.Tomcat.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.catalina.Cluster;
import org.apache.catalina.Engine;
import org.apache.catalina.Executor;
import org.apache.catalina.Host;
import org.apache.catalina.Realm;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.pidster.tomcat.embed.TomcatServerBuilder;
import org.pidster.tomcat.embed.TomcatServiceBuilder;


public class TomcatServiceBuilderImpl extends AbstractContainerBuilder<TomcatServerBuilder, TomcatServiceBuilder> 
    implements TomcatServiceBuilder {

    private final Service service;

    private final Engine engine;

    private final AtomicInteger connectorCount = new AtomicInteger(0);

    private final AtomicInteger executorCount = new AtomicInteger(0);

    protected TomcatServiceBuilderImpl(TomcatServerBuilderImpl parent, String name, Map<String, String> config) {
        super(parent);

        String sclassName = "org.apache.catalina.core.StandardService";
        String eclassName = "org.apache.catalina.core.StandardEngine";

        this.service = InstanceConfigurer.instantiate(loader(), Service.class, sclassName, new HashMap<String, String>());
        this.engine = InstanceConfigurer.instantiate(loader(), Engine.class, eclassName, config);
        engine.setName(name);
        service.setName(engine.getName());

        setContainer(engine);
    }

    @Override
    public TomcatServiceBuilder collect(Host child) {
        if (engine.getDefaultHost() == null || "".equals(engine.getDefaultHost())) {
            engine.setDefaultHost(child.getName());
        }
        child.setParent(engine);
        engine.addChild(child);
        return this;
    }

    @Override
    public TomcatServerBuilder parent() {
        engine.setService(service);
        service.setContainer(engine);
        return super.parent().collect(service);
    }

    @Override
    public TomcatServiceBuilder addStandardConnectors() {

        Map<String, String> nconfig = new HashMap<String, String>();
        // standard config if reqd
        addConnector(PROTOCOL_NIO, 8080, nconfig);

        Map<String, String> aconfig = new HashMap<String, String>();
        // standard config if reqd
        addConnector(PROTOCOL_AJP, 8009, aconfig);

        return this;
    }

    @Override
    public TomcatServiceBuilder addBioExecutor(int port, int minSize, int maxSize, Map<String, String> config) {
        String name = String.format("tomcat-embed-exec-%s", executorCount.get());
        String prefix = String.format("embed-exec-%s", executorCount.get());
        addExecutor(name, prefix, minSize, maxSize, config);

        config.put("executor", name);
        addConnector(PROTOCOL_BIO, port, config);

        return this;
    }

    @Override
    public TomcatServiceBuilder addNioExecutor(int port, int minSize, int maxSize, Map<String, String> config) {
        String name = String.format("tomcat-embed-exec-%s", executorCount.get());
        String prefix = String.format("embed-exec-%s", executorCount.get());
        addExecutor(name, prefix, minSize, maxSize, config);

        config.put("executor", name);
        addConnector(PROTOCOL_NIO, port, config);

        return this;
    }

    @Override
    public TomcatServiceBuilder addExecutor(String name, String prefix, int minSize, int maxSize, Map<String, String> config) {
        String className = "org.apache.catalina.core.StandardThreadExecutor";
        Executor executor = InstanceConfigurer.instantiate(loader(), Executor.class, className, config);
        service.addExecutor(executor);
        executorCount.incrementAndGet();
        return this;
    }

    @Override
    public TomcatServiceBuilder addConnector(String protocol, int port) {
        return addConnector(protocol, port, EMPTY);
    }

    @Override
    public TomcatServiceBuilder addConnector(String protocol, int port, Map<String, String> config) {
        Connector connector = new Connector(protocol);
        connector.setPort(port);
        if (service.findExecutors().length > 0) {
            //
        }

        InstanceConfigurer.configure(connector, config);
        connector.setService(service);
        service.addConnector(connector);
        connectorCount.incrementAndGet();
        return this;
    }

    @Override
    public TomcatServiceBuilder setCluster(Cluster cluster) {
        cluster.setContainer(engine);
        engine.setCluster(cluster);
        return this;
    }

    @Override
    public TomcatServiceBuilder setRealm(Realm realm) {
        realm.setContainer(engine);
        engine.setRealm(realm);
        return this;
    }

    @Override
    public TomcatHostBuilderImpl addHost(String name, String appBase) {

        Map<String, String> config = new HashMap<>();
        config.put("name", name);
        config.put("appBase", appBase);

        return new TomcatHostBuilderImpl(this, config);
    }

}
