package org.pidster.tomcat.embed.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatApplicationBuilder;
import org.pidster.tomcat.embed.TomcatHostBuilder;
import org.pidster.tomcat.embed.TomcatServiceBuilder;


public class TomcatHostBuilderImpl extends AbstractContainerBuilder<TomcatServiceBuilder, TomcatHostBuilder> implements Builder<Tomcat>, TomcatHostBuilder {

    private final Host host;

    private final Map<String, TomcatApplicationBuilder> applications = new HashMap<>();

    protected TomcatHostBuilderImpl(TomcatServiceBuilderImpl parent, Map<String, String> config) {
        super(parent);
        String className = "org.apache.catalina.core.StandardHost";
        this.host = InstanceConfigurer.instantiate(loader(), Host.class, className, config);

        setContainer(host);
    }

    @Override
    public TomcatHostBuilder collect(Context child) {
        child.setParent(host);
        host.addChild(child);
        return this;
    }

    @Override
    public TomcatServiceBuilder parent() {
        return super.parent().collect(host);
    }

    @Override
    public TomcatHostBuilder addApplication(String path, String name, String docBase, Map<String, String> config) {

        if (applications.containsKey(path)) {
            throw new IllegalStateException("Path already exists: " + path);
        }

        Map<String, String> aconfig = new HashMap<>();
        if (config != null && !config.isEmpty()) {
            aconfig.putAll(config);
        }

        aconfig.put("path", path);
        aconfig.put("name", name);
        aconfig.put("docBase", docBase);

        TomcatApplicationBuilderImpl applicationBuilder = new TomcatApplicationBuilderImpl(this, aconfig);

        // return this
        return applicationBuilder.parent();
    }

    @Override
    public TomcatApplicationBuilder createApplication(String path, String name, Map<String, String> config) {

        if (applications.containsKey(path)) {
            throw new IllegalStateException("Path already exists: " + path);
        }

        Map<String, String> aconfig = new HashMap<>();
        if (config != null && !config.isEmpty()) {
            aconfig.putAll(config);
        }

        aconfig.put("path", path);
        aconfig.put("name", name);
        aconfig.put("docBase", name);

        return new TomcatApplicationBuilderImpl(this, aconfig);
    }

}
