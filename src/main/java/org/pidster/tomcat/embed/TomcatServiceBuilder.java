package org.pidster.tomcat.embed;

import java.util.Map;

import org.apache.catalina.Host;


public interface TomcatServiceBuilder extends TomcatContainerBuilder<TomcatServerBuilder, TomcatServiceBuilder>, Collector<TomcatServiceBuilder, Host> {

    TomcatServiceBuilder addStandardConnectors();

    TomcatServiceBuilder addBioExecutor(int port, int minSize, int maxSize, Map<String, String> config);

    TomcatServiceBuilder addNioExecutor(int port, int minSize, int maxSize, Map<String, String> config);

    TomcatServiceBuilder addExecutor(String name, String prefix, int minSize, int maxSize, Map<String, String> config);

    TomcatServiceBuilder addConnector(String protocol, int port);

    TomcatServiceBuilder addConnector(String protocol, int port, Map<String, String> config);

    TomcatHostBuilder addHost(String name, String appBase);

}