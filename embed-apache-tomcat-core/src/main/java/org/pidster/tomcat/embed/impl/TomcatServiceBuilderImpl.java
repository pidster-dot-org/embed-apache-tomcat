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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.catalina.Engine;
import org.apache.catalina.Executor;
import org.apache.catalina.Host;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.AbstractProtocol;
import org.pidster.tomcat.embed.Tomcat;
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
        addConnector(PROTOCOL_NIO, Tomcat.DEFAULT_HTTP_PORT, nconfig);

        Map<String, String> aconfig = new HashMap<String, String>();
        // standard config if reqd
        addConnector(PROTOCOL_AJP, Tomcat.DEFAULT_AJP_PORT, aconfig);

        return this;
    }

    @Override
    public TomcatServiceBuilder addBioExecutor(int port, int minSize, int maxSize, Map<String, String> config) {
        String name = String.format("tomcat-embed-exec-%s", executorCount.get());
        String prefix = String.format("embed-exec-%s", executorCount.get());
        addExecutor(name, prefix, minSize, maxSize, config);

        config.put("executorName", name);
        addConnector(PROTOCOL_BIO, port, config);

        return this;
    }

    @Override
    public TomcatServiceBuilder addNioExecutor(int port, int minSize, int maxSize, Map<String, String> config) {
        String name = String.format("tomcat-embed-exec-%s", executorCount.get());
        String prefix = String.format("embed-exec-%s", executorCount.get());
        addExecutor(name, prefix, minSize, maxSize, config);

        config.put("executorName", name);
        addConnector(PROTOCOL_NIO, port, config);

        return this;
    }

    @Override
    public TomcatServiceBuilder addExecutor(String name, String prefix, int minSize, int maxSize, Map<String, String> config) {
        String className = "org.apache.catalina.core.StandardThreadExecutor";
        Map<String, String> c = new HashMap<>();
        c.putAll(config);
        c.put("name", name);
        c.put("namePrefix", prefix);
        Executor executor = InstanceConfigurer.instantiate(loader(), Executor.class, className, c);
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
        if (service.findExecutors().length > 0 && config != null && config.containsKey("executorName")) {
        	Executor executor = service.getExecutor(config.get("executorName"));
            if (executor != null) {
            	AbstractProtocol protocolHandler = (AbstractProtocol) connector.getProtocolHandler();
            	protocolHandler.setExecutor(executor);

            	Set<Entry<String, String>> entrySet = config.entrySet();
            	for (Entry<String, String> entry : entrySet) {
            		protocolHandler.setProperty(entry.getKey(), entry.getValue());
            	}
            }
        }

        InstanceConfigurer.configure(connector, config);

        if (config != null) {
        	Set<Entry<String, String>> entrySet = config.entrySet();
        	for (Entry<String, String> entry : entrySet) {
        		connector.setProperty(entry.getKey(), entry.getValue());
        	}
        }

        connector.setService(service);
        service.addConnector(connector);
        connectorCount.incrementAndGet();
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
